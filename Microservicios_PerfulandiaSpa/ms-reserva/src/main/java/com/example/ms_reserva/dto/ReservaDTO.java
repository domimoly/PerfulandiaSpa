package com.example.ms_reserva.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReservaDTO {

    @NotNull(message = "La puntuacion es obligatoria")
    @Min(value = 1, message = "La puntuacion minima es 1")
    @Max(value = 5, message = "La puntuacion maxima es 5")
    private Integer puntuacion;

    private String comentario;
    private String fechaResena;

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;

}
