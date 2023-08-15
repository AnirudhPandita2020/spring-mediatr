package com.anirudh.springmediatr.exception;

import com.anirudh.springmediatr.core.spring.annotation.EnableMediatr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.anirudh.springmediatr.exception")
@EnableMediatr
public class SpringMediatRExceptionApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringMediatRExceptionApplication.class);
    }
}
