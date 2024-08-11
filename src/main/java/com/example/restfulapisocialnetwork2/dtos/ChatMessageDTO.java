package com.example.restfulapisocialnetwork2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Builder
public class ChatMessageDTO {
    @JsonProperty("user_id_A")
    private Long userIdA;
    @JsonProperty("user_id_B")
    private Long userIdB;
    @JsonProperty("content")
    private String content;
}
