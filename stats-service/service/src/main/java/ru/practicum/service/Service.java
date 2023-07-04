package ru.practicum.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("ru.practicum.library")
@ComponentScan("ru.practicum")
public class Service {
    public static void main(String[] args) {
        SpringApplication.run(Service.class, args);
    }
}
