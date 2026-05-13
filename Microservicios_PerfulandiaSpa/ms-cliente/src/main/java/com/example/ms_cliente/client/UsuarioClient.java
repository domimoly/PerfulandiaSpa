package com.example.ms_cliente.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_cliente.dto.ApiResponse;
import com.example.ms_cliente.dto.UsuarioResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8083/api/usuarios";

    public UsuarioResponse obtenerUsuario(Long id, String token) {

        ApiResponse<UsuarioResponse> response = webClient.get()
            .uri(BASE_URL + id)
            .header("Authorization", token)
            .retrieve()
            .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ApiResponse<UsuarioResponse>>() {})
            .block();
        return response != null ? response.getData() : null;
    }


}
