package com.blogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.blogger")
public class BloggerApplication {
    public static void main(String[] args) {

        SpringApplication.run(BloggerApplication.class, args);
    }
}