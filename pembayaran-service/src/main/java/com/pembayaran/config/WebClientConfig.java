package com.pembayaran.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

//    @Bean
//    public WebClient webClientProduct(WebClient.Builder builder) {
//        return builder.baseUrl("http://localhost:8080/api/").build();
//    }
    @Bean
    public WebClient webClienttrxD(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8084/api/")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
