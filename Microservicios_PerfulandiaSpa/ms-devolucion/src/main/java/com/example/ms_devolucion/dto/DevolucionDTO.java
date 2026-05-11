package com.example.ms_devolucion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DevolucionDTO {

    @NotBlank(message = "La fecha de devolución es obligatoria")
    private String fechaDevolucion;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
