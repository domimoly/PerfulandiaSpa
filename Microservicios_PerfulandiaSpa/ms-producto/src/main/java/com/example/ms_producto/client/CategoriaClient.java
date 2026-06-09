package com.example.ms_producto.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_producto.dto.ApiResponse;
import com.example.ms_producto.dto.CategoriaResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoriaClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8094/api/v2/categorias/";

    public CategoriaResponse obtenerCategoria(Long id, String token) {
        ApiResponse<CategoriaResponse> response = webClient.get()
                .uri(BASE_URL + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ApiResponse<CategoriaResponse>>() {})
                .block();

        return response != null ? response.getData() : null;
    }
}