package com.aireview;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 备忘录后端启动类。
 */
@SpringBootApplication
@MapperScan("com.aireview.mapper")
public class AiReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiReviewApplication.class, args);
    }
}
