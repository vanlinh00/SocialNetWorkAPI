package com.example.restfulapisocialnetwork2.responses;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
@Data
public class CommentListResponse {
    private List<CommentResponse> commentResponseList;
    private int isBlock;
}
