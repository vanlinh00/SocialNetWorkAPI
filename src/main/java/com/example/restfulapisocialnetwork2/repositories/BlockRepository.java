package com.example.restfulapisocialnetwork2.repositories;

import com.example.restfulapisocialnetwork2.models.Block;
import com.example.restfulapisocialnetwork2.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {

    // Check if a block relationship exists between the user and the blocked user
    boolean existsByUserIdAndBlockedUserId(Long userId, Long blockedUserId);

    // Delete the block relationship between the user and the blocked user
    void deleteByUserIdAndBlockedUserId(Long userId, Long blockedUserId);
}
