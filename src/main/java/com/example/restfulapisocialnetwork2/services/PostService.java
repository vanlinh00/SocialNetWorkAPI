package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.PostDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;
    @Override
    public Post createPost(PostDTO postDTO) throws DataNotFoundException {

        return null;
    }
}
