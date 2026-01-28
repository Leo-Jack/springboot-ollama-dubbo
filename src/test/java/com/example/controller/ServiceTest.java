package com.example.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dubbo.ChatService;
import com.example.dubbo.HelloService;

@SpringBootTest
class ServiceTest {

    @DubboReference(version = "1.0.0", check = false)
    private ChatService chatService;
    
    @DubboReference(version = "1.0.0", check = false)
    private HelloService helloService;

    @Test
    void testChatService() {
        String message = "你好，请问你是谁？";
        
        try {
            String response = chatService.chat(message);
            assertNotNull(response, "Response should not be null");
            assertFalse(response.isEmpty(), "Response should not be empty");
            System.out.println("Chat response: " + response);
        } catch (Exception e) {
            fail("Chat service call failed: " + e.getMessage());
        }
    }
    
    @Test
    void testHelloService() {
        String result = helloService.sayHello("Leo");
        assertNotNull(result, "Result should not be null");
        System.out.println("Hello service response: " + result);
    }

}
