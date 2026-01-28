# Spring Boot Ollama Dubbo 项目

一个基于 Spring Boot 3.3.8 + Java 21 + Apache Dubbo 3.2.9 的应用示例，集成本地 Ollama Qwen2.5 模型，展示如何通过 Dubbo RPC 框架调用 AI 服务。

## 📋 项目特点

- **Java 21 LTS** - 最新长期支持版本
- **Spring Boot 3.3.8** - 现代化的 Spring 框架
- **Apache Dubbo 3.2.9** - 高性能 RPC 框架
- **Ollama Qwen2.5** - 本地 AI 模型服务
- **Spring AOP** - 面向切面编程实现拦截和分析
- **UTF-8 编码** - 完整支持中文

## 🏗️ 项目结构

```
src/main/java/com/example/
├── Application.java                     # 主启动类（启用 Dubbo）
├── SimpleClient.java                    # 简单一次性调用客户端
├── HelloClient.java                     # HelloWorld 服务调用客户端
├── controller/
│   └── ChatController.java             # REST API 控制器
├── dubbo/
│   ├── ChatService.java                # 聊天服务接口（Dubbo）
│   ├── ChatServiceImpl.java             # 聊天服务实现
│   ├── HelloService.java               # Hello 服务接口（Dubbo）
│   └── HelloServiceImpl.java            # Hello 服务实现
├── service/
│   ├── OllamaService.java              # Ollama 服务接口（Dubbo）
│   └── impl/
│       └── OllamaServiceImpl.java       # Ollama 服务实现
├── dto/
│   ├── OllamaRequest.java              # Ollama 请求 DTO
│   └── OllamaResponse.java             # Ollama 响应 DTO
└── aop/
    └── HelloServiceAspect.java         # AOP 切面（拦截并通过 Ollama 分析调用）

src/main/resources/
└── application.yml                     # 应用配置

src/test/java/com/example/
└── controller/
    └── ServiceTest.java                # 服务集成测试
```

## 🔧 核心功能

### 1. ChatService（聊天服务）
- 接口：`com.example.dubbo.ChatService`
- 通过 Dubbo 调用 Ollama Qwen2.5 模型
- 支持对话和问答

### 2. HelloService（问候服务）
- 接口：`com.example.dubbo.HelloService`
- 简单的 Hello World 演示
- 演示 Dubbo 基本功能

### 3. HelloServiceAspect（AOP 切面）
- 拦截 HelloService 的所有方法调用
- 捕获请求参数和返回值
- 自动调用 Ollama 分析方法的功能

### 4. REST API（ChatController）
- 端点：`POST /api/chat?message={prompt}`
- 通过 HTTP 调用聊天服务
- 返回 JSON 格式的响应

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

# 在另一个终端运行简单客户端（一次性调用）
mvn spring-boot:run -Dspring-boot.run.mainClass=com.example.SimpleClient

# 或运行 HelloWorld 客户端
mvn spring-boot:run -Dspring-boot.run.mainClass=com.example.HelloClient
```

## 📡 API 使用示例

### REST API 调用

```bash
# 调用聊天 API
curl -X POST "http://localhost:8080/api/chat?message=你好，请介绍一下自己"

# 响应示例
{
  "success": true,
  "message": "我是来自阿里云的语言模型Qwen...",
  "error": null
}
```

### Dubbo 直接调用

```java
@DubboReference(version = "1.0.0", check = false)
private ChatService chatService;

String response = chatService.chat("你好");
```

## 🧪 测试

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=ServiceTest
```

## 📊 配置说明

### application.yml

```yaml
spring:
  application:
    name: simple-spring-app

server:
  port: 8080
  servlet:
    encoding:
      charset: UTF-8
      enabled: true
      force: true

dubbo:
  application:
    name: ollama-dubbo-provider
  protocol:
    name: dubbo
    port: 20880
  registry:
    address: N/A          # 点对点直连，不使用注册中心
  scan:
    base-packages: com.example.dubbo

ollama:
  url: http://localhost:11434
  model: qwen2.5:0.5b
  timeout: 30000
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
客户端
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

## 📝 主要类说明

### Application.java
- 主启动类
- 启用 `@EnableDubbo` 注解激活 Dubbo 功能

### HelloServiceAspect.java
- AOP 切面类
- 拦截 HelloService 的所有方法
- 捕获请求参数、返回值
- 调用 Ollama 分析方法的功能

### OllamaServiceImpl.java
- 真正与 Ollama API 交互的实现
- 使用 Java 21 的 HttpClient
- 配置参数通过 `@Value` 注解从 application.yml 读取

## 🐛 常见问题

### Q: 启动时报 "Ollama 连接失败"
A: 确保本地 Ollama 服务已启动，且模型已拉取

### Q: 中文乱码
A: 确保 IDE 和终端编码设置为 UTF-8

### Q: Dubbo 服务无法找到
A: 确保应用启动类添加了 `@EnableDubbo` 注解

## 📚 相关文档

- [Apache Dubbo 官网](https://dubbo.apache.org)
- [Spring Boot 官网](https://spring.io/projects/spring-boot)
- [Ollama 官网](https://ollama.ai)
- [Java 21 新特性](https://openjdk.org/projects/jdk/21/)

## 📄 许可证

MIT License

## 👨‍💻 作者

Created with Java 21 and Spring Boot 3.3.8
│   │   │               ├── dto
│   │   │               │   └── RequestDto.java
│   │   │               └── model
│   │   │                   └── ResponseModel.java
│   │   └── resources
│   │       ├── application.yml
│   │       └── logback-spring.xml
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── ollamadubbo
│                       └── OllamaServiceTests.java
├── pom.xml
├── Dockerfile
├── .gitignore
└── README.md
```

## Setup Instructions

1. **Clone the repository:**
   ```
   git clone <repository-url>
   cd my-spring-ollama-dubbo
   ```

2. **Build the project:**
   ```
   mvn clean install
   ```

3. **Run the application:**
   ```
   mvn spring-boot:run
   ```

4. **Access the API:**
   The application will be running on `http://localhost:8080`. You can interact with the Ollama service through the endpoints defined in `OllamaController`.

## Usage Guidelines

- The `OllamaService` interface defines the methods available for interacting with the Ollama service.
- The `OllamaClient` class is responsible for making HTTP calls to the local Ollama service.
- The `DubboServer` and `DubboClient` classes facilitate service registration and discovery using Dubbo.

## Logging

Logging is configured using Logback. You can modify the logging settings in `logback-spring.xml`.

## Docker

To build a Docker image for the application, run:
```
docker build -t my-spring-ollama-dubbo .
```

## Contributing

Feel free to submit issues or pull requests for any improvements or bug fixes.