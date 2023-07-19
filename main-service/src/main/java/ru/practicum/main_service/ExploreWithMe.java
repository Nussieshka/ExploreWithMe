package ru.practicum.main_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.practicum.client.StatsClient;

@SpringBootApplication
@Import(StatsClient.class)
public class ExploreWithMe {
    public static final String APP_NAME = "main_service";

    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMe.class, args);
    }
}