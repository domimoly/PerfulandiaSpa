package com.example.ms_cliente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClienteDTO {

    @NotBlank(message = "La fecha de registro es obligatoria")
    private String fechaRegistro;

    @NotBlank(message = "La dirección de envío es obligatoria")
    private String direccionEnvio;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

}
