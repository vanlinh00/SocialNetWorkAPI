package com.example.restfulapisocialnetwork2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CommentDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("comment")
    private String comment;
}
