package com.example.restfulapisocialnetwork2.responses;

import com.example.restfulapisocialnetwork2.models.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@Data
public class UserResponse {

    private Long id;
    private String name;
    private String avatar;

    public static UserResponse fromPost(User user) {
        UserResponse userPesponse = UserResponse.builder()
                .id(user.getId())
                .name(user.getFullName()).build();
        return userPesponse;
    }
}
