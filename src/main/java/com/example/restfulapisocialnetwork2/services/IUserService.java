package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.UserDTO;
import com.example.restfulapisocialnetwork2.dtos.UserInfoDTO;
import com.example.restfulapisocialnetwork2.dtos.UserVerificationDTO;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.models.UserInfo;
import com.example.restfulapisocialnetwork2.responses.UserResponse;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;

    String login(String phoneNumber, String password) throws Exception;

    String generateVerificationCode() throws Exception;

    void sendVerificationCode(long userId) throws Exception;

    ResponseEntity<?> checkVerifyCode(
            UserVerificationDTO userVerificationDTO
    ) throws Exception;

    User getUser(String phoneNumber) throws Exception;

    UserResponse getUser(Long id) throws Exception;

    void blacklistToken(String token) throws Exception;

    // Method to check if a token is blacklisted
    boolean isTokenBlacklisted(String token) throws Exception;

    //UserInfo setUserInfo(UserInfoDTO userInfoDTO) throws Exception;
}
