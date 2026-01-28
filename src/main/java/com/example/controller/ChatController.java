package com.example.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dubbo.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    @DubboReference(version = "1.0.0", check = false)
    private ChatService chatService;

    @PostMapping
    public ChatResponse chat(@RequestParam String message) {
        try {
            String response = chatService.chat(message);
            return new ChatResponse(true, response, null);
        } catch (Exception e) {
            return new ChatResponse(false, null, e.getMessage());
        }
    }

    @GetMapping("/health")
    public String health() {
        return "Ollama Chat Service is running";
    }

    public static class ChatResponse {
        private boolean success;
        private String message;
        private String error;

        public ChatResponse(boolean success, String message, String error) {
            this.success = success;
            this.message = message;
            this.error = error;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public String getError() {
            return error;
        }
    }
}
