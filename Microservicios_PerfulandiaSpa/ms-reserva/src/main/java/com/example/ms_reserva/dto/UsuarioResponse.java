package com.example.ms_reserva.dto;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String username;
    private String email;
    private String tipo;
    
}
