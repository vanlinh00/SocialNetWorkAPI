package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.PostDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;

    @Override
    public Post createPost(PostDTO postDTO, User user) throws DataNotFoundException {
        Post newPost = Post.builder()
                .userId(user.getId())
                .described(postDTO.getDescribed())
                .build();
        return postRepository.save(newPost);
    }

    @Override
    public Post getPost(long id) throws DataNotFoundException {

        Optional<Post> listPosts = postRepository.findById(id);
        if (listPosts.isEmpty()) {
            throw new DataIntegrityViolationException("Don't exists this post");
        }
        return listPosts.get();
    }


}
