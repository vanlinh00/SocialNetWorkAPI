package com.example.restfulapisocialnetwork2.responses;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Data
public class FriendResponse {
    private Long id;
    private String userName;
    private String avatar;
    private LocalDateTime created;
}
