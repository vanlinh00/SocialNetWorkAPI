package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.components.JwtTokenUtil;
import com.example.restfulapisocialnetwork2.dtos.UserDTO;
import com.example.restfulapisocialnetwork2.dtos.UserVerificationDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.exceptions.PermissionDenyException;
import com.example.restfulapisocialnetwork2.models.Role;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.models.VerificationCode;
import com.example.restfulapisocialnetwork2.repositories.RoleRepository;
import com.example.restfulapisocialnetwork2.repositories.UserRepository;
import com.example.restfulapisocialnetwork2.repositories.VerificationCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

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

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("phone number already exists");
        }
//        // convert from UserDTO=> User
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getGoogleAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .Email(userDTO.getEmail())
                .build();

        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() ->
                        new DataNotFoundException("Role not found"));

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
            throw new DataNotFoundException("Invalid phoneNumber or password");
        }
        User existingUser = optionalUser.get();
        if (existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0) {
            if (!passWordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password,
                existingUser.getAuthorities() // Role user
        );  // tạo ra đôi tượng authenticationToken
        //authenticate with Java Spring security   // xác thực với sring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public void sendVerificationCode(long userId) throws DataNotFoundException {
        String strVerificationCode = generateVerificationCode();
        User exitingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find user with id: " + userId
                        ));
        sendVerificationEmail(exitingUser.getEmail(), strVerificationCode);
        VerificationCode verificationCode = VerificationCode.builder(
        ).user(exitingUser).vertificationCode(strVerificationCode).build();
        verificationCodeRepository.save(verificationCode);
    }

    @Override
    public String generateVerificationCode() throws DataNotFoundException {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void sendVerificationEmail(String toEmail, String verificationCode) throws DataNotFoundException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verify Code");
        message.setText("Your Code is " + verificationCode);
        mailSender.send(message);
    }

    @Override
    public ResponseEntity<?> checkVerifyCode(
            UserVerificationDTO userVerificationDTO
    ) throws DataNotFoundException {
        User exitingUser = userRepository.findById(userVerificationDTO.getUserId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find user with id: " + userVerificationDTO.getUserId()
                        ));

            return ResponseEntity.ok("1");
    }

}
