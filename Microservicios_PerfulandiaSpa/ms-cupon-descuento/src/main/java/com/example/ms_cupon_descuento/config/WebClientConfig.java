package com.example.ms_cupon_descuento.config;

// Arreglar | No se puede aplicar porque tiene problemas con el Auth xd
import org.springframework.context.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration

public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
    
}