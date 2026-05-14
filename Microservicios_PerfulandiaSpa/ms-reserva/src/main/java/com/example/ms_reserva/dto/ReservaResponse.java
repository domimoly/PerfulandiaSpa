package com.example.ms_reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservaResponse {
    private Long id;
    private Integer puntuacion;
    private String comentario;
    private String fechaResena;
    private UsuarioResponse usuario;

}
