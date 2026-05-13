package com.example.ms_inventario.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_inventario.dto.ApiResponse;
import com.example.ms_inventario.dto.ProductoReponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductoClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8088/ms-producto/";

    public ProductoReponse obtenerProducto(Long id, String token) {
        ApiResponse<ProductoReponse> response = webClient.get()
                .uri(BASE_URL + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ApiResponse<ProductoReponse>>() {})
                .block();

        return response != null ? response.getData() : null;
    }
}
