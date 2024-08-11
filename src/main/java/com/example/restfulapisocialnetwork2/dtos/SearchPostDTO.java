package com.example.restfulapisocialnetwork2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SearchPostDTO {
    @JsonProperty("keyword")
    private String KeyWord;

    @JsonProperty("index")
    private int index;

    @JsonProperty("count")
    private int count;
}
