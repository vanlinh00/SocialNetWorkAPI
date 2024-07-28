package com.example.restfulapisocialnetwork2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserVerificationDTO {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("verification_code")
    private String verificationCode;
}
