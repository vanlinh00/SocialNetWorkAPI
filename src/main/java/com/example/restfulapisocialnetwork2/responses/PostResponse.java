package com.example.restfulapisocialnetwork2.responses;

import com.example.restfulapisocialnetwork2.models.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@Data
public class PostResponse extends BaseReponse {

    private Long id;
    private String described;
    private int like;
    private int comment;

    @JsonProperty("is_like")
    private int isLike;
    private ImageListResponse imageListResponse;
    private UserResponse author;
    private int state;
    @JsonProperty("is_blocked")
    private int isBlocked;
    @JsonProperty("can_edit")
    private int canEdit;
    @JsonProperty("can_comment")
    private int canComment;

    public static PostResponse fromPost(Post post, UserResponse userOwner,
                                        Long IdUserLogin, Boolean blockUser) {
        int isUserLike = post.getLikes().stream()
                .anyMatch(like -> like.getUserId().equals(IdUserLogin)) ? 1 : 0;
        List<ImageResponse> imageResponseList = new ArrayList<>();
        for (int i = 0; i < post.getImages().size(); i++) {
            imageResponseList.add(ImageResponse.fromImage(post.getImages().get(i)));
        }
        int state = 1;
        int isBlocked = blockUser ? 1 : 0;
        int canEdit = (IdUserLogin == userOwner.getId()) ? 1 : 0;
        int canComment = (!blockUser || canEdit == 1) ? 1 : 0;

        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .described(post.getDescribed())
                .like(post.getLikes().size())
                .comment(post.getComments().size())
                .isLike(isUserLike)
                .imageListResponse(ImageListResponse
                        .builder()
                        .imageResponseList(imageResponseList)
                        .countImage(imageResponseList.size())
                        .build())
                .author(userOwner)
                .state(state)
                .isBlocked(isBlocked)
                .canEdit(canEdit)
                .canComment(canComment)
                .build();
        postResponse.setCreateAt(post.getCreatedAt());
        postResponse.setUpdateAt(post.getUpdateAt());
        return postResponse;
    }
}
