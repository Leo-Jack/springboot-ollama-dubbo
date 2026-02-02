package com.example.aop;

import java.util.Arrays;

import org.apache.dubbo.config.annotation.DubboReference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.example.dubbo.ChatService;

/**
 * HelloService AOP 切面
 * 用于拦截 HelloService 的方法调用，并通过 Ollama 对调用服务进行分析
 */
@Aspect
@Component
public class HelloServiceAspect {

    @DubboReference(version = "1.0.0", check = false)
    private ChatService chatService;

    /**
     * 定义切点：拦截 HelloService 接口的所有方法
     */
    @Pointcut("execution(* com.example.dubbo.HelloService.*(..))")
    public void helloServicePointcut() {
    }

    /**
     * 环绕通知：在方法调用前后进行拦截，并调用 Ollama 分析
     */
    @Around("helloServicePointcut()")
    public Object interceptHelloService(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        // 打印请求信息
        System.out.println("\n ===== 请求开始 =====");
        System.out.println(" 方法名: " + methodName);
        System.out.println(" 请求参数: " + Arrays.toString(args));
        
        long startTime = System.currentTimeMillis();
        
        try {
            // 执行原方法
            Object result = joinPoint.proceed();
            
            long endTime = System.currentTimeMillis();
            
            // 打印响应信息
            System.out.println(" 返回值: " + result);
            System.out.println(" 执行时间: " + (endTime - startTime) + "ms");
            
            // 构建询问 Ollama 的提示词
            String prompt = "我调用了一个方法: " + methodName + 
                           ", 请求参数是: " + Arrays.toString(args) + 
                           ", 返回值是: " + result + 
                           "。请简短地说明这次调用做了什么？";
            
            System.out.println(" 询问 Ollama: " + prompt);
            
            // 调用 Ollama 分析
            try {
                String analysis = chatService.chat(prompt);
                System.out.println(" Ollama 分析结果: " + analysis);
            } catch (Exception e) {
                System.out.println(" Ollama 调用失败: " + e.getMessage());
            }
            
            System.out.println(" ===== 请求成功 =====\n");
            
            return result;
        } catch (Throwable e) {
            long endTime = System.currentTimeMillis();
            
            // 打印异常信息
            System.out.println(" 异常类型: " + e.getClass().getName());
            System.out.println(" 异常消息: " + e.getMessage());
            System.out.println(" 执行时间: " + (endTime - startTime) + "ms");
            System.out.println(" ===== 请求失败 =====\n");
            
            throw e;
        }
    }
}

