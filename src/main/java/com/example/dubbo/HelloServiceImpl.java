package com.example.dubbo;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * Hello World Dubbo 服务实现
 */
@DubboService(version = "1.0.0")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello() {
        return "Hello World";
    }

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
