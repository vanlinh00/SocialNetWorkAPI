package com.example.restfulapisocialnetwork2.repositories;


import com.example.restfulapisocialnetwork2.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    //  List<ChatMessage> findByUserIdAAndUserIdBOrderByCreatedAtAsc(Long userIdA, Long userIdB);
    @Query("SELECT c FROM ChatMessage c WHERE (c.userIdA = :userIdA AND c.userIdB = :userIdB) OR (c.userIdA = :userIdB AND c.userIdB = :userIdA) ORDER BY c.createdAt ASC")
    List<ChatMessage> findChatMessagesBetweenUsers(@Param("userIdA") Long userIdA, @Param("userIdB") Long userIdB);
}
