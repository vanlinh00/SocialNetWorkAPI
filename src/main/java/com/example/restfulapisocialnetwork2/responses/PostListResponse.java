package com.example.restfulapisocialnetwork2.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
@Builder
public class PostListResponse {
    private List<PostResponse> postResponseList;
    @JsonProperty("new_items")
    private int newItems;
    @JsonProperty("last_id")
    private int lastId;
}
