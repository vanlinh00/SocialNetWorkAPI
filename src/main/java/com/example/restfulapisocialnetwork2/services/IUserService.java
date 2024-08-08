package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.UserDTO;
import com.example.restfulapisocialnetwork2.dtos.UserVerificationDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.models.VerificationCode;
import com.example.restfulapisocialnetwork2.responses.UserResponse;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;

    String login(String phoneNumber, String password) throws Exception;

    String generateVerificationCode() throws DataNotFoundException;

    void sendVerificationCode(long userId) throws DataNotFoundException;

    ResponseEntity<?> checkVerifyCode(
            UserVerificationDTO userVerificationDTO
    ) throws DataNotFoundException;

    User GetUser(String phoneNumber) throws Exception;

    UserResponse GetUser(Long id) throws Exception;

    void blacklistToken(String token) throws Exception;

    // Method to check if a token is blacklisted
    public boolean isTokenBlacklisted(String token) throws Exception;
}
