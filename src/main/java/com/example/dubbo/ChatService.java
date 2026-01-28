package com.example.dubbo;

/**
 * Dubbo 服务接口
 */
public interface ChatService {
    
    /**
     * 聊天接口
     * @param message 用户消息
     * @return AI 回复
     */
    String chat(String message);
}
