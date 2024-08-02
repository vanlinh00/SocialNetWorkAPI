package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.PostDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.User;

import java.util.List;

public interface IPostService {
    Post createPost(PostDTO postDTO, User user) throws DataNotFoundException;

    Post getPost(long id) throws DataNotFoundException;

    List<Post> GetListPost(Long index, int count) throws DataNotFoundException;
}
