package com.example.restfulapisocialnetwork2.repositories;
import com.example.restfulapisocialnetwork2.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    // Phương thức để lấy một trang Post
    Page<Post> findAll(Pageable pageable);
}
