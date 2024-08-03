package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.PostDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.responses.PostResponse;

import java.util.List;

public interface IPostService {
    Post createPost(PostDTO postDTO, User user) throws DataNotFoundException;

    PostResponse getPost(long id) throws Exception;

    List<PostResponse> GetListPost(Long index, int count) throws Exception;
}
