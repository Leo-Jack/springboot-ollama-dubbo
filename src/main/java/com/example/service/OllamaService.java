package com.example.service;

/**
 * Ollama 服务接口
 */
public interface OllamaService {
    
    /**
     * 调用 Ollama API 进行对话
     * @param prompt 用户输入
     * @return AI 回复
     * @throws Exception 调用异常
     */
    String chat(String prompt) throws Exception;
}
