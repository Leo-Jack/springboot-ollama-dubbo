package com.example.dubbo;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import com.example.service.OllamaService;

/**
 * Dubbo 服务实现
 */
@DubboService(version = "1.0.0", timeout = 30000)
public class ChatServiceImpl implements ChatService {
    
    @DubboReference(version = "1.0.0", check = false)
    private OllamaService ollamaService;

    @Override
    public String chat(String message) {
        try {
            return ollamaService.chat(message);
        } catch (Exception e) {
            throw new RuntimeException("Chat failed: " + e.getMessage(), e);
        }
    }
}
