package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.ChatMessageDTO;
import com.example.restfulapisocialnetwork2.models.ChatMessage;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.repositories.ChatMessageRepository;

import com.example.restfulapisocialnetwork2.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getChatMessagesBetweenUsers(Long userIdA, Long userIdB) {
        return chatMessageRepository.findChatMessagesBetweenUsers(userIdA, userIdB);
    }

    public ChatMessage sendMessage(ChatMessageDTO messageDTO) {
        ChatMessage message = ChatMessage.builder()
                .userIdA(messageDTO.getUserIdA())
                .userIdB(messageDTO.getUserIdB())
                .content(messageDTO.getContent())
                .build();
        return chatMessageRepository.save(message);
    }
}
