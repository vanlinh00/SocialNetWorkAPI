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
public class ImageListResponse {
    private List<ImageResponse> imageResponseList;
    private int countImage;

}
