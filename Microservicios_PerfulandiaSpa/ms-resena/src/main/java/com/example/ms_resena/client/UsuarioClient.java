package com.example.ms_resena.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.ms_resena.dto.UsuarioResponse;
import com.example.ms_resena.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8095/api/v2/usuarios/";

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
