package com.example.restfulapisocialnetwork2.components;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSession{

    private final UserService userService;
    private String phoneNumber;
    private User user;

    public void User(String phoneNumber) throws Exception {
        user= userService.GetUser(phoneNumber);
    }
    public User GetUser(){
        return user;
    }

}
