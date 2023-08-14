package com.anirudh.springmediatr.spring.normal;

import com.anirudh.springmediatr.spring.annotation.EnableMediatr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMediatr
public class SpringMediatRTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringMediatRTestApplication.class);
    }
}
