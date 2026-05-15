package com.example.ms_inventario.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_inventario.dto.ApiResponse;
import com.example.ms_inventario.dto.SucursalResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SucursalClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8091/api/sucursales/";

    public SucursalResponse obtenerSucursal(Long id, String token) {
        ApiResponse<SucursalResponse> response = webClient.get()
                .uri(BASE_URL + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ApiResponse<SucursalResponse>>() {})
                .block();

        return response != null ? response.getData() : null;
    }
}