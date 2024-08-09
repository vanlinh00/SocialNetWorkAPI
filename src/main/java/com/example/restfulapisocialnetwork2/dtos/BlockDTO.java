package com.example.restfulapisocialnetwork2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BlockDTO {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("type")
    private int type;
}
