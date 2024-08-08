package com.example.restfulapisocialnetwork2.services;

import org.springframework.http.ResponseEntity;

public interface ILikeService {
     ResponseEntity<?> likePost(Long postId, Long userId) throws Exception;
}
