package com.example.restfulapisocialnetwork2.repositories;
import com.example.restfulapisocialnetwork2.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
