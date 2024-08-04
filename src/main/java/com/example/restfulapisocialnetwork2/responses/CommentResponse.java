package com.example.restfulapisocialnetwork2.responses;

import com.example.restfulapisocialnetwork2.models.Comment;
import com.example.restfulapisocialnetwork2.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@Data
public class CommentResponse extends BaseReponse {

    private Long id;
    private Long postId;
    private String content;
    private UserResponse user;

    public static CommentResponse fromComment(Comment comment, UserResponse userResponse) {
        CommentResponse commentResponse = CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(userResponse)
                .build();
        commentResponse.setCreateAt(comment.getCreatedAt());
        commentResponse.setUpdateAt(comment.getUpdateAt());
        return commentResponse;
    }
}
