package com.ai_service.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private ChatClient chatClient;

    public AiService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String chat(String question) {

        return chatClient
                .prompt()
                .user(question)
                .call()
                .content();
    }
}
