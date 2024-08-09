package com.example.restfulapisocialnetwork2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@Data
public class AcceptFriendDTO {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("is_accept")
    private int isAccept;
}
