package com.example.restfulapisocialnetwork2.responses;

import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.User;
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
    private boolean is_like;// 1 like 0 chua like
    private List<Media> images;
    private List<Media> videos;
    private UserPesponse user;
    private boolean state;  // trang thai cua nguoi viet
    private boolean is_blocked; // khiem tra author co block userid khong
    private boolean can_edit;  // khiem tra toekn co the edit bai hay khong
    private boolean banned;
    private boolean can_comment; // coo the comment duoc bai viet khong

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

    public static PostResponse fromPost(Post post, UserPesponse user) {
        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .described(post.getDescribed())
                .build();
        postResponse.setCreateAt(post.getCreatedAt());
        postResponse.setUpdateAt(post.getUpdateAt());
        postResponse.setUser(user);
        return postResponse;
    }
}
