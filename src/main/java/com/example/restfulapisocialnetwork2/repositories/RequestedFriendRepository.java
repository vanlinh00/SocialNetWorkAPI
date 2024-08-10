package com.example.restfulapisocialnetwork2.repositories;

import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.RequestedFriend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestedFriendRepository extends JpaRepository<RequestedFriend, Long> {
    List<RequestedFriend> findByUserIdA(Long userIdA);

    @Query("SELECT p FROM RequestedFriend p WHERE p.userIdA  = :userIdA")
    Page<RequestedFriend> findByUserIdA(@Param("userIdA") Long userIdA, Pageable pageable);

    boolean existsByUserIdAAndUserIdB( Long userIdA, Long userIdB);

    void deleteByUserIdAAndUserIdB( Long userIdA, Long userIdB);
}
