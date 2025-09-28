package com.example.springboothello.config;

import io.github.supabase.SupabaseClient;
import io.github.supabase.SupabaseClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupabaseConfig {
    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Bean
    public SupabaseClient supabaseClient() {
        return new SupabaseClientBuilder(supabaseUrl, supabaseKey).build();
    }
}