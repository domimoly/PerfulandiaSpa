package com.example.ms_inventario.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_inventario.dto.ApiResponse;
import com.example.ms_inventario.dto.ProveedorResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProveedorClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8089/api/v2/proveedores/";

    public ProveedorResponse obtenerProveedor(Long id, String token) {
        ApiResponse<ProveedorResponse> response = webClient.get()
                .uri(BASE_URL + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ApiResponse<ProveedorResponse>>() {})
                .block();

        return response != null ? response.getData() : null;
    }
}