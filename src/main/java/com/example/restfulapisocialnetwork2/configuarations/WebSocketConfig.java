package com.example.restfulapisocialnetwork2.configuarations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
     //   registry.addHandler(chatWebSocketHandler(), "/chat")
            //    .setAllowedOrigins("*"); // Cấu hình cho phép các nguồn gốc khác nhau
    }

//    @Bean
//    public WebSocketHandler chatWebSocketHandler() {
//       // return new ChatWebSocketHandler();
//    }
}
