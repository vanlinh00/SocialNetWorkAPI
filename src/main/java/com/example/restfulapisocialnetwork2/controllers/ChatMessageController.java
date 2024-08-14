package com.example.restfulapisocialnetwork2.controllers;

import com.example.restfulapisocialnetwork2.dtos.ChatMessageDTO;
import com.example.restfulapisocialnetwork2.models.ChatMessage;
import com.example.restfulapisocialnetwork2.services.ChatMessageService;
import com.example.restfulapisocialnetwork2.services.WebSocketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("${api.prefix}/chat")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final WebSocketService webSocketService;

    @GetMapping("/{userIdA}/{userIdB}")
    public ResponseEntity<List<ChatMessage>> getMessages(
            @Valid
            @PathVariable Long userIdA, @PathVariable Long userIdB) {
        List<ChatMessage> chatMessages = chatMessageService.getChatMessagesBetweenUsers(userIdA, userIdB);
        return ResponseEntity.ok(chatMessages);
    }

    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessageDTO messageDTO) {
        ChatMessage message = chatMessageService.sendMessage(messageDTO);

        // Send the message via WebSocket
        try {
            String formattedMessage = messageDTO.getUserIdA() + ": " + messageDTO.getContent();
            webSocketService.sendMessageToUser(messageDTO.getUserIdB(), formattedMessage);
        } catch (IOException e) {
            e.printStackTrace();  // Handle exceptions properly in production
        }

        return ResponseEntity.ok(message);
    }
}
