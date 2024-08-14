package com.example.restfulapisocialnetwork2.configuarations;

import com.example.restfulapisocialnetwork2.services.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@AllArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketService webSocketService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = getUserIdFromSession(session);
        webSocketService.addSession(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = getUserIdFromSession(session);
        webSocketService.removeSession(userId);
    }

    private Long getUserIdFromSession(WebSocketSession session) {
        // Extract user ID from session attributes or authentication
        return (Long) session.getAttributes().get("userId");
    }
}
