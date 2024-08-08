package com.example.restfulapisocialnetwork2.repositories;
import com.example.restfulapisocialnetwork2.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository  extends JpaRepository<Image, Long> {
    List<Image> findByPostId(Long postId);
}
