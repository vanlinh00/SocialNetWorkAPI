package com.example.restfulapisocialnetwork2.repositories;

import com.example.restfulapisocialnetwork2.models.Friend;
import com.example.restfulapisocialnetwork2.models.RequestedFriend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    //Page<Friend> findByUserIdA(@Param("userIdA") Long userIdA, Pageable pageable);

    @Query("SELECT f FROM Friend f WHERE f.userIdA = :userId OR f.userIdB = :userId")
    Page<Friend> findByUserId(@Param("userId") Long userId, Pageable pageable);

    //boolean existsByUserIdAAndUserIdB( Long userIdA, Long userIdB);
    boolean existsByUserIdAAndUserIdBOrUserIdAAndUserIdB(Long userIdA1, Long userIdB1, Long userIdA2, Long userIdB2);
    long countByUserIdA(Long userIdA);
}
