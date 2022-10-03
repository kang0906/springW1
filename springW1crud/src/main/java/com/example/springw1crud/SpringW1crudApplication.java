package com.example.springw1crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringW1crudApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringW1crudApplication.class, args);
    }
}
