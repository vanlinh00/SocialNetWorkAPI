package com.example.restfulapisocialnetwork2.controllers;

import com.example.restfulapisocialnetwork2.dtos.UserDTO;
import com.example.restfulapisocialnetwork2.dtos.UserLoginDTO;
import com.example.restfulapisocialnetwork2.dtos.UserVerificationDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.exceptions.PermissionDenyException;
import com.example.restfulapisocialnetwork2.models.Role;
import com.example.restfulapisocialnetwork2.models.VerificationCode;
import com.example.restfulapisocialnetwork2.repositories.RoleRepository;
import com.example.restfulapisocialnetwork2.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("${api.prefix}/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Password does not match");
            }
            userService.createUser(userDTO);
            return ResponseEntity.ok("Registers succesfully");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid
            @RequestBody UserLoginDTO userLoginDTO) {
        try {
            String Token = userService.login(
                    userLoginDTO.getPhoneNumber()
                    , userLoginDTO.getPassWord());
            return ResponseEntity.ok(Token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sendVerificationCode/{id}")
    public ResponseEntity<String> sendVerificationCode(
            @Valid
            @PathVariable long id) {
        try {
            userService.sendVerificationCode(id);
            return ResponseEntity.ok("Send to your email is succes");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/checkVerificationCode")
    public ResponseEntity<String> checkVerificationCode(
            @Valid @RequestBody UserVerificationDTO userVerificationDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> erroreMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
            }
            userService.checkVerifyCode(userVerificationDTO);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//    @GetMapping("/logout")
//    public ResponseEntity<String> logout(
//    ){
//        try {
//
//        } catch (Exception e){
//
//        }
//    }
}
