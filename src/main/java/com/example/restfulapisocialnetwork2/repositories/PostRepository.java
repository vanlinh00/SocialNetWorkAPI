package com.example.restfulapisocialnetwork2.repositories;
import com.example.restfulapisocialnetwork2.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post,Long> {
    // Phương thức để lấy một trang Post
    Page<Post> findAll(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.userId  = :userId")
    Page<Post> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
