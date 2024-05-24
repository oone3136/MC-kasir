package com.kasir.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8082/api").build();
    }
    @Bean

    public WebClient webClientLaporan(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8081/api").build();
    }
}
