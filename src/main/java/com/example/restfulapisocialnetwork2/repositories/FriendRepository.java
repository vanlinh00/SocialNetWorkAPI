package com.example.restfulapisocialnetwork2.repositories;

import com.example.restfulapisocialnetwork2.models.Friend;
import com.example.restfulapisocialnetwork2.models.RequestedFriend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Page<Friend> findByUserIdA(@Param("userIdA") Long userIdA, Pageable pageable);
}
