package com.example.restfulapisocialnetwork2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserLoginDTO {

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("password")
    private String passWord;

}
