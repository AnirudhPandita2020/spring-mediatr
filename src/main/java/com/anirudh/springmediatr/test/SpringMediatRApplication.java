package com.anirudh.springmediatr.test;

import com.anirudh.springmediatr.core.EnableMediatr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMediatr
public class SpringMediatRApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMediatRApplication.class, args);
    }

}
