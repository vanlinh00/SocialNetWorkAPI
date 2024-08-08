package com.example.restfulapisocialnetwork2.repositories;

import com.example.restfulapisocialnetwork2.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    Optional<Comment> findByUserIdAndPostId(Long id, Long postId);
}
