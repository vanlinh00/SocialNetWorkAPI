package com.example.restfulapisocialnetwork2.services;
import com.example.restfulapisocialnetwork2.dtos.UserDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password) throws Exception;

    void sendVerificationCode(String email) throws DataNotFoundException;

    String generateVerificationCode() throws DataNotFoundException;

    void sendVerificationEmail(String toEmail, String verificationCode) throws DataNotFoundException;
}
