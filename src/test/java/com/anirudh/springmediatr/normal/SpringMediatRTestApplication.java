package com.anirudh.springmediatr.normal;

import com.anirudh.springmediatr.core.spring.annotation.EnableMediatr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.anirudh.springmediatr.normal"})
@EnableMediatr
public class SpringMediatRTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringMediatRTestApplication.class);
    }
}
