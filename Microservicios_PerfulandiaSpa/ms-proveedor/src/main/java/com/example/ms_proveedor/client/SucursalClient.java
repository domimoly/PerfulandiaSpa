package com.example.ms_proveedor.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_proveedor.dto.SucursalResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SucursalClient {
    private final WebClient.Builder webClientBuilder;

    public SucursalResponse obtenerSucursal(Long id, String token) {
        try {
            return webClientBuilder.build()
                .get()
                .uri("http://localhost:8091/api/sucursales/" + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(SucursalResponse.class)
                .block();
        } catch (Exception e) {
            return null;
        }
    }
}