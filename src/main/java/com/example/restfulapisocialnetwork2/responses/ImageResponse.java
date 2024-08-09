package com.example.restfulapisocialnetwork2.responses;

import com.example.restfulapisocialnetwork2.models.Comment;
import com.example.restfulapisocialnetwork2.models.Image;
import com.example.restfulapisocialnetwork2.models.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@Data
public class ImageResponse {
    private Long id;
    private Long postId;
    private String linkImage;

    public static ImageResponse fromImage(Image image) {
        ImageResponse imageResponse = ImageResponse.builder()
                .id(image.getId())
                .postId(image.getPost().getId())
                .linkImage(image.getLinkImage())
                .build();
        return imageResponse;
    }
}
