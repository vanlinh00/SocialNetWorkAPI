package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.exceptions.ResourceNotFoundException;
import com.example.restfulapisocialnetwork2.models.Like;
import com.example.restfulapisocialnetwork2.repositories.LikeRepository;
import com.example.restfulapisocialnetwork2.repositories.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LikeService implements ILikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public ResponseEntity<?> likePost(Long postId, Long userId) throws Exception {
        if (!postRepository.existsById(postId)) {
            new ResourceNotFoundException("This post does not exist");
        }
        if (likeRepository.existsByPostIdAndUserId(postId, userId)) {
            likeRepository.deleteByPostIdAndUserId(postId, userId);
            return ResponseEntity.ok().body("Post unliked successfully");
        } else {
            Like like = new Like();
            like.setPost(postRepository.findById(postId).orElseThrow(
                    () -> new ResourceNotFoundException("Post not found")
            ));
            like.setUserId(userId);
            likeRepository.save(like);
            return ResponseEntity.ok().body("Post liked successfully");
        }
    }
}
