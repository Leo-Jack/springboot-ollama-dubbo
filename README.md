# Spring Boot Ollama Dubbo 项目

一个基于 Spring Boot 3.3.8 + Java 21 + Apache Dubbo 3.2.9 的应用示例，集成本地 Ollama Qwen2.5 模型，展示如何通过 Dubbo RPC 框架调用 AI 服务。

## 🔧 核心功能

### 1. ChatService（聊天服务）
- 接口：[`com.example.dubbo.ChatService`](http://localhost:8080/api/chat/health)
- 通过 Dubbo 调用 Ollama Qwen2.5 模型
- 支持对话和问答

### 2. HelloServiceAspect（分析服务调用服务）
- 拦截 HelloService 的所有方法调用
- 拦截接口：[`com.example.dubbo.HelloService`](http://localhost:8080/api/chat/hello)
- 捕获请求参数和返回值
- 自动调用 Ollama 分析方法的功能

## 🚀 快速开始

### 前置要求
- Java 21+
- Maven 3.6+
- 本地 Ollama 服务（已启动）
- Ollama 已拉取 `qwen2.5:0.5b` 模型

### 环境配置

```bash
# 下载 Ollama
# https://ollama.ai

# 拉取 Qwen 模型
ollama pull qwen2.5:0.5b

# 启动 Ollama 服务
ollama serve
```

### 编译和运行

```bash
# 编译项目
mvn clean install

# 运行主应用（发布 Dubbo 服务）
mvn spring-boot:run -Dspring-boot.run.mainClass=com.example.Application
```

## 📡 使用示例

```
# 浏览器访问 http://localhost:8080/api/hello

# 浏览器响应 Hello World
控制台会返回服务调用分析：
 ===== 请求开始 =====
 方法名: sayHello
 请求参数: []
 返回值: Hello World
 执行时间: 0ms
 询问 Ollama: 我调用了一个方法: sayHello, 请求参数是: [], 返回值是: Hello World。请简短地说明这次调用做了什么？
 Ollama 分析结果: 这个调用实现了"sayHello"方法的单一请求，且没有参数传递或请求体（即方法的实际参数）。所以，这次调用只是发送了一个简单的字符串"Hello World"到指
定的方法上。
 ===== 请求成功 =====
```

## 🎯 工作流程

### ChatService 调用流程
```
REST API (HTTP)
    ↓
ChatController
    ↓ (Dubbo RPC)
ChatService (Dubbo 消费者)
    ↓ (Dubbo RPC)
ChatServiceImpl (Dubbo 提供者)
    ↓
OllamaService (Dubbo RPC)
    ↓
OllamaServiceImpl
    ↓ (HTTP)
Ollama API
```

### HelloService + AOP 调用流程
```
REST API (HTTP)
    ↓
HelloController
    ↓ (Dubbo RPC)
HelloService (经过 AOP 拦截)
    ↓
HelloServiceImpl
    ↓
AOP 切面记录请求/返回
    ↓
调用 ChatService 分析
    ↓
返回结果
```

## 🔌 依赖版本

- Spring Boot: 3.3.8
- Apache Dubbo: 3.2.9
- Java: 21
- Jackson: 2.17.0