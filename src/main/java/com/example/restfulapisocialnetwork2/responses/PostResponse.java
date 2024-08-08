package com.example.restfulapisocialnetwork2.responses;

import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
@Data
public class PostResponse extends BaseReponse {
    private Long id;
    private String described;
    private int like; // so luong like
    private int comment; // so luong comment
    @JsonProperty("is_like")
    private int isLike;// 1 like 0 chua like
    private List<Media> images;
    private List<Media> videos;
    private UserResponse author;
    private int state;  // trang thai cua nguoi viet
    @JsonProperty("is_blocked")
    private int isBlocked; // khiem tra author co block userid khong
    @JsonProperty("can_edit")
    private int canEdit;  // khiem tra toekn co the edit bai hay khong
    private int banned;
    @JsonProperty("can_comment")
    private int canComment; // coo the comment duoc bai viet khong

    // image
    // id
    // url

    // video
    // url
    // thumb

    // author
    // id
    // name
    // avatar

    public static PostResponse fromPost(Post post, UserResponse userOwner, Long IdUserLogin) {
        int isUserLike = post.getLikes().stream()
                .anyMatch(like -> like.getUserId().equals(IdUserLogin)) ? 1 : 0;
//        List<Media> images = null;
//        List<Media> videos = null;
//        int state = 1;  // trang thai cua nguoi viet
//        int isBlocked = 0; // khiem tra author co block userid khong
        int canEdit = (IdUserLogin == userOwner.getId()) ? 1 : 0;
//        int banned = 0;
//        int canComment = 0; // coo the comment duoc bai viet khong

        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .described(post.getDescribed())
                .like(post.getLikes().size())
                .comment(post.getComments().size())
                .isLike(isUserLike)
//                .images(images)
//                .videos(videos)
                .author(userOwner)
//                .state(state)
//                .isBlocked(isBlocked)
                .canEdit(canEdit)
//                .banned(banned)
//                .canComment(canComment)
                .build();
        postResponse.setCreateAt(post.getCreatedAt());
        postResponse.setUpdateAt(post.getUpdateAt());
        return postResponse;
    }
}
