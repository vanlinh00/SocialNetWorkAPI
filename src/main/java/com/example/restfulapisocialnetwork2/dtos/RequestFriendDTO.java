package com.example.restfulapisocialnetwork2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RequestFriendDTO {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("index")
    private Long index;

    @JsonProperty("count")
    private Long count;
}
