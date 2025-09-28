package com.example.springboothello.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class TestConfig {
    @Bean
    public WebClient supabaseClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080") // Mock URL for testing
                .build();
    }
}