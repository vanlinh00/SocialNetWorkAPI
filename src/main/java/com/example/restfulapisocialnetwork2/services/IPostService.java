package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.*;
import com.example.restfulapisocialnetwork2.models.Image;
import com.example.restfulapisocialnetwork2.models.Report;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.responses.PostListResponse;
import com.example.restfulapisocialnetwork2.responses.PostResponse;

public interface IPostService {
    void createPost(PostDTO postDTO, User user) throws Exception;

    PostResponse getPost(long id) throws Exception;

    PostListResponse GetListPost(Long index, int count) throws Exception;

    void updatePost(PostEditDTO postEditDTO) throws Exception;

    void deleterPost(Long id) throws Exception;

    Report reportPost(ReportDTO reportDTO) throws Exception;

    Image createPostImage(
            Long postId,
            PostImageDTO postImageDTO) throws Exception;

    PostListResponse searchPost(SearchPostDTO searchPostDTO) throws Exception;
}
