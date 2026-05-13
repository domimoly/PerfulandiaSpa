package com.example.ms_ticket_soporte.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TicketSoporteDTO {

    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    private String estado;
    private String fechaTicket;

    @NotNull(message = "El clienteId es obligatorio")
    private Long clienteId;
}
