package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.PostDTO;
import com.example.restfulapisocialnetwork2.dtos.PostEditDTO;
import com.example.restfulapisocialnetwork2.dtos.ReportDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.Report;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.responses.CommentListResponse;
import com.example.restfulapisocialnetwork2.responses.PostListResponse;
import com.example.restfulapisocialnetwork2.responses.PostResponse;

import java.util.List;

public interface IPostService {
    Post createPost(PostDTO postDTO, User user) throws DataNotFoundException;

    PostResponse getPost(long id) throws Exception;

    PostListResponse GetListPost(Long index, int count) throws Exception;

    Post updatePost(PostEditDTO postEditDTO) throws Exception;

    void deleterPost(Long id) throws DataNotFoundException;

    Report reportPost(ReportDTO reportDTO) throws DataNotFoundException;
}
