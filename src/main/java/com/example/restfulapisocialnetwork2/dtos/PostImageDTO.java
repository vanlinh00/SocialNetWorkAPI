package com.example.restfulapisocialnetwork2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImageDTO {
    @JsonProperty("post_id")
    @Min(value = 1, message = "Product's ID must be > 0")
    private Long postId;

    @Size(min = 5, max = 200, message = "Image's name")
    @JsonProperty("link_image")
    private String linkImage;
}
