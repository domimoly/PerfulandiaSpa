package com.example.ms_ticket_soporte.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketSoporteResponse {
    private Long id;
    private String asunto;
    private String mensaje;
    private String estado;
    private String fechaTicket;
    private ClienteResponse cliente;
}
