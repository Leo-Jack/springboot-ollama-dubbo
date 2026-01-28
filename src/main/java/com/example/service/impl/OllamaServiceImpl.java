package com.example.service.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

import com.example.dto.OllamaRequest;
import com.example.dto.OllamaResponse;
import com.example.service.OllamaService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Ollama 服务实现
 */
@DubboService(version = "1.0.0", timeout = 30000)
public class OllamaServiceImpl implements OllamaService {
    
    @Value("${ollama.url}")
    private String ollamaUrl;
    
    @Value("${ollama.model}")
    private String ollamaModel;
    
    @Value("${ollama.timeout}")
    private int ollamaTimeout;
    
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public OllamaServiceImpl() {
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
    }

    @Override
    public String chat(String prompt) throws Exception {
        OllamaRequest request = new OllamaRequest(ollamaModel, prompt);
        String requestJson = objectMapper.writeValueAsString(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(ollamaUrl + "/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                .timeout(Duration.ofSeconds(ollamaTimeout))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            OllamaResponse ollamaResponse = objectMapper.readValue(response.body(), OllamaResponse.class);
            return ollamaResponse.getResponse();
        } else {
            throw new RuntimeException("Ollama API returned status code: " + response.statusCode());
        }
    }
}
