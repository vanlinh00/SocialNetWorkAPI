package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.components.JwtTokenUtil;
import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.UserDTO;
import com.example.restfulapisocialnetwork2.dtos.UserInfoDTO;
import com.example.restfulapisocialnetwork2.dtos.UserVerificationDTO;
import com.example.restfulapisocialnetwork2.exceptions.InvalidCredentialsException;
import com.example.restfulapisocialnetwork2.exceptions.ResourceNotFoundException;
import com.example.restfulapisocialnetwork2.models.Role;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.models.UserInfo;
import com.example.restfulapisocialnetwork2.models.VerificationCode;
import com.example.restfulapisocialnetwork2.repositories.RoleRepository;
import com.example.restfulapisocialnetwork2.repositories.UserInfoRepository;
import com.example.restfulapisocialnetwork2.repositories.UserRepository;
import com.example.restfulapisocialnetwork2.repositories.VerificationCodeRepository;
import com.example.restfulapisocialnetwork2.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passWordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private JavaMailSender mailSender;
    private final VerificationCodeRepository verificationCodeRepository;
    private final Set<String> blacklistedTokens = new HashSet<>();
    private final UserInfoRepository userInfoRepository;


    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("phone number already exists");
        }
        // convert from UserDTO=> User
        User newUser = User.builder().fullName(
                        userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getGoogleAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .Email(userDTO.getEmail()
                ).build();
        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(
                () -> new ResourceNotFoundException("Role not found"));
//        if (role.getName().toUpperCase().equals(Role.ADMIN))
//        {
//            throw new PermissionDenyException("You cannot register an admin account");
//        }
        newUser.setRole(role);
        // Khiem tra neu co AccountId, khong yeu cau password
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            //Spring Security
            String encodePassword = passWordEncoder.encode(password);
            newUser.setPassword(encodePassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {

        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new InvalidCredentialsException("Invalid phoneNumber or password");
        }
        User existingUser = optionalUser.get();
        if (existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0) {
            if (!passWordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, password, existingUser.getAuthorities() // Role user
        );  // tạo ra đôi tượng authenticationToken
        //authenticate with Java Spring security   // xác thực với sring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }


    @Override
    public void sendVerificationCode(long userId) throws Exception {
        String strVerificationCode = generateVerificationCode();
        User exitingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find user with id: " + userId)
                );
        sendVerificationEmail(exitingUser.getEmail(), strVerificationCode);
        VerificationCode verificationCode = VerificationCode.builder().user(exitingUser).vertificationCode(strVerificationCode).build();
        verificationCodeRepository.save(verificationCode);
    }

    @Override
    public String generateVerificationCode() throws Exception {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void sendVerificationEmail(String toEmail, String verificationCode)
            throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verify Code");
        message.setText("Your Code is " + verificationCode);
        mailSender.send(message);
    }

    @Override
    public ResponseEntity<?> checkVerifyCode(UserVerificationDTO userVerificationDTO)
            throws Exception {
        User exitingUser = userRepository.findById(userVerificationDTO.getUserId()).
                orElseThrow(
                        () -> new ResourceNotFoundException("Cannot find user with id: " + userVerificationDTO.getUserId()));

        List<VerificationCode> ListVerification = verificationCodeRepository.findByUserId(exitingUser.getId());
        if (!ListVerification.isEmpty()) {
            VerificationCode lastVerificationCode = ListVerification.get(ListVerification.size() - 1);

            if (lastVerificationCode.getVertificationCode().equals(userVerificationDTO.getVerificationCode())) {
                return ResponseEntity.ok("Correct");
            }
        }
        throw new DataIntegrityViolationException("UnCorrect");
    }

    @Override
    public User getUser(String phoneNumber) throws Exception {

        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new InvalidCredentialsException("Invalid phoneNumber or password");
        }
        User existingUser = optionalUser.get();
        return existingUser;
    }

    @Override
    public UserResponse getUser(Long id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new InvalidCredentialsException("Invalid phoneNumber or password");
        }
        User existingUser = optionalUser.get();
        UserResponse userResponse = UserResponse.fromPost(existingUser);
        return userResponse;
    }


    @Override
    // Method to add a token to the blacklist
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    // Method to check if a token is blacklisted
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

}
