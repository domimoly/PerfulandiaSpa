package com.example.ms_ticket_soporte.ControllerTest;

import com.example.ms_ticket_soporte.controller.TicketSoporteController;
import com.example.ms_ticket_soporte.dto.ClienteResponse;
import com.example.ms_ticket_soporte.dto.TicketSoporteDTO;
import com.example.ms_ticket_soporte.dto.TicketSoporteResponse;
import com.example.ms_ticket_soporte.service.TicketSoporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketSoporteController.class)
@AutoConfigureMockMvc(addFilters = false)
class TicketSoporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TicketSoporteService tsService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    private static final String TOKEN = "Bearer test-token";

    @Test
    void debeListarTickets() throws Exception {
        ClienteResponse cliente = new ClienteResponse();
        cliente.setId(1L);
        cliente.setFechaRegistro("2026-01-10");
        cliente.setDireccionEnvio("Av. Principal 456");

        TicketSoporteResponse ticket = TicketSoporteResponse.builder()
                .id(1L)
                .asunto("Problema con pedido")
                .mensaje("No llegó mi pedido")
                .estado("Abierto")
                .fechaTicket("2026-05-01")
                .cliente(cliente)
                .build();

        when(tsService.listar(TOKEN)).thenReturn(List.of(ticket));

        mockMvc.perform(get("/api/tickets")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].asunto").value("Problema con pedido"))
                .andExpect(jsonPath("$.data[0].estado").value("Abierto"))
                .andExpect(jsonPath("$.data[0].cliente.id").value(1));
    }

    @Test
    void debeObtenerTicketPorId() throws Exception {
        TicketSoporteResponse ticket = TicketSoporteResponse.builder()
                .id(1L)
                .asunto("Problema con pedido")
                .mensaje("No llegó mi pedido")
                .estado("Abierto")
                .fechaTicket("2026-05-01")
                .build();

        when(tsService.obtener(1L, TOKEN)).thenReturn(ticket);

        mockMvc.perform(get("/api/tickets/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.asunto").value("Problema con pedido"))
                .andExpect(jsonPath("$.data.mensaje").value("No llegó mi pedido"));
    }

    @Test
    void debeCrearTicket() throws Exception {
        TicketSoporteDTO dto = new TicketSoporteDTO();
        dto.setAsunto("Problema con pedido");
        dto.setMensaje("No llegó mi pedido");
        dto.setEstado("Abierto");
        dto.setFechaTicket("2026-05-01");
        dto.setClienteId(1L);

        TicketSoporteResponse creado = TicketSoporteResponse.builder()
                .id(1L)
                .asunto("Problema con pedido")
                .mensaje("No llegó mi pedido")
                .estado("Abierto")
                .fechaTicket("2026-05-01")
                .build();

        when(tsService.crear(any(TicketSoporteDTO.class), eq(TOKEN))).thenReturn(creado);

        mockMvc.perform(post("/api/tickets")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Ticket creado"))
                .andExpect(jsonPath("$.data.asunto").value("Problema con pedido"))
                .andExpect(jsonPath("$.data.estado").value("Abierto"));
    }

    @Test
    void debeRetornar400SiDatosInvalidos() throws Exception {
        TicketSoporteDTO dtoInvalido = new TicketSoporteDTO();

        mockMvc.perform(post("/api/tickets")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void debeActualizarTicket() throws Exception {
        TicketSoporteDTO dto = new TicketSoporteDTO();
        dto.setAsunto("Perfume dañado");
        dto.setMensaje("El frasco llegó roto");
        dto.setEstado("En proceso");
        dto.setFechaTicket("2026-05-02");
        dto.setClienteId(1L);

        TicketSoporteResponse actualizado = TicketSoporteResponse.builder()
                .id(1L)
                .asunto("Perfume dañado")
                .mensaje("El frasco llegó roto")
                .estado("En proceso")
                .fechaTicket("2026-05-02")
                .build();

        when(tsService.actualizar(eq(1L), any(TicketSoporteDTO.class), eq(TOKEN)))
                .thenReturn(actualizado);

        mockMvc.perform(put("/api/tickets/1")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Ticket actualizado"))
                .andExpect(jsonPath("$.data.asunto").value("Perfume dañado"))
                .andExpect(jsonPath("$.data.estado").value("En proceso"));
    }

    @Test
    void debeEliminarTicket() throws Exception {
        doNothing().when(tsService).eliminar(1L);

        mockMvc.perform(delete("/api/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Ticket eliminado"));
    }

}
