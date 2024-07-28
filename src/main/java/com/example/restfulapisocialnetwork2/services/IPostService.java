package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.PostDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Post;

public interface IPostService {
    Post createPost(PostDTO postDTO) throws DataNotFoundException;
}
