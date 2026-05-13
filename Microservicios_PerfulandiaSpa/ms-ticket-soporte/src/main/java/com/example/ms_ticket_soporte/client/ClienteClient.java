package com.example.ms_ticket_soporte.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_ticket_soporte.dto.ApiResponse;
import com.example.ms_ticket_soporte.dto.ClienteResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8092/api/clientes/";

    public ClienteResponse obtenerCliente(Long id, String token) {
        ApiResponse<ClienteResponse> response = webClient.get()
                .uri(BASE_URL + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ApiResponse<ClienteResponse>>() {})
                .block();

        return response != null ? response.getData() : null;
    }
}
