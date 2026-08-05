package com.harmonia_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient spotifyWebClient() {
        return WebClient.builder()
                .baseUrl("https://accounts.spotify.com")
                .build();
    }

    @Bean
    WebClient spotifyApiWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.spotify.com/v1")
                .build();
    }

    @Bean
    WebClient groqWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .build();
    }

}