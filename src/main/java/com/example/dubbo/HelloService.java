package com.example.dubbo;

/**
 * 简单的 Hello World Dubbo 服务接口
 */
public interface HelloService {
    
    /**
     * 返回 Hello World 消息
     * @return Hello World 字符串
     */
    String sayHello();
    
    /**
     * 返回带名字的问候
     * @param name 名字
     * @return 问候消息
     */
    String sayHello(String name);
}
