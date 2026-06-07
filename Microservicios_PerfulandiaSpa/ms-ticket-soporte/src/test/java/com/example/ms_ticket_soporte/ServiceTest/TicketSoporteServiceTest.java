package com.example.ms_ticket_soporte.ServiceTest;


import com.example.ms_ticket_soporte.client.ClienteClient;
import com.example.ms_ticket_soporte.dto.ClienteResponse;
import com.example.ms_ticket_soporte.dto.TicketSoporteDTO;
import com.example.ms_ticket_soporte.dto.TicketSoporteResponse;
import com.example.ms_ticket_soporte.model.TicketSoporte;
import com.example.ms_ticket_soporte.repository.TicketSoporteRepository;
import com.example.ms_ticket_soporte.service.TicketSoporteService;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketSoporteServiceTest {

    @Mock
    private TicketSoporteRepository tsRepo;

    @Mock
    private ClienteClient clienteClient;

    @InjectMocks
    private TicketSoporteService service;

    private final String token = "Bearer test";

    @Test
    void deberiaObtenerTicketCuandoExiste() {

        TicketSoporte ticket = new TicketSoporte();
        ticket.setId(1L);
        ticket.setAsunto("Problema con pedido");
        ticket.setMensaje("No llegó mi pedido");
        ticket.setEstado("Abierto");
        ticket.setFechaTicket("2026-05-01");
        ticket.setClienteId(1L);

        ClienteResponse cliente = new ClienteResponse();
        cliente.setId(1L);
        cliente.setFechaRegistro("2026-01-10");
        cliente.setDireccionEnvio("Av. Principal 456");

        when(tsRepo.findById(1L))
                .thenReturn(Optional.of(ticket));

        when(clienteClient.obtenerCliente(1L, token))
                .thenReturn(cliente);

        TicketSoporteResponse resultado = service.obtener(1L, token);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Problema con pedido", resultado.getAsunto());

        verify(tsRepo).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoTicketNoExiste() {

        when(tsRepo.findById(99L))
                .thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> service.obtener(99L, token)
        );

        assertEquals("Ticket no encontrado", ex.getMessage());

        verify(tsRepo).findById(99L);
    }

    @Test
    void deberiaRetornarListaTickets() {

        TicketSoporte ticket = new TicketSoporte();
        ticket.setId(1L);
        ticket.setAsunto("Problema con pedido");
        ticket.setMensaje("No llegó mi pedido");
        ticket.setEstado("Abierto");
        ticket.setFechaTicket("2026-05-01");
        ticket.setClienteId(1L);

        ClienteResponse cliente = new ClienteResponse();
        cliente.setId(1L);
        cliente.setFechaRegistro("2026-01-10");
        cliente.setDireccionEnvio("Av. Principal 456");

        when(tsRepo.findAll())
                .thenReturn(List.of(ticket));

        when(clienteClient.obtenerCliente(1L, token))
                .thenReturn(cliente);

        List<TicketSoporteResponse> resultado = service.listar(token);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());

        verify(tsRepo).findAll();
    }

    @Test
    void deberiaCrearTicketCorrectamente() {

        TicketSoporteDTO dto = new TicketSoporteDTO();
        dto.setAsunto("Problema con pedido");
        dto.setMensaje("No llegó mi pedido");
        dto.setEstado("Abierto");
        dto.setFechaTicket("2026-05-01");
        dto.setClienteId(1L);

        ClienteResponse cliente = new ClienteResponse();
        cliente.setId(1L);
        cliente.setFechaRegistro("2026-01-10");
        cliente.setDireccionEnvio("Av. Principal 456");

        TicketSoporte ticketGuardado = new TicketSoporte();
        ticketGuardado.setId(1L);
        ticketGuardado.setAsunto(dto.getAsunto());
        ticketGuardado.setMensaje(dto.getMensaje());
        ticketGuardado.setEstado(dto.getEstado());
        ticketGuardado.setFechaTicket(dto.getFechaTicket());
        ticketGuardado.setClienteId(dto.getClienteId());

        when(clienteClient.obtenerCliente(1L, token))
                .thenReturn(cliente);

        when(tsRepo.save(any(TicketSoporte.class)))
                .thenReturn(ticketGuardado);

        TicketSoporteResponse resultado = service.crear(dto, token);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Problema con pedido", resultado.getAsunto());

        verify(tsRepo).save(any(TicketSoporte.class));
    }

    @Test
    void deberiaActualizarTicketCorrectamente() {

        TicketSoporte existente = new TicketSoporte();
        existente.setId(1L);
        existente.setAsunto("Asunto Antiguo");
        existente.setMensaje("Mensaje Antiguo");
        existente.setEstado("Abierto");
        existente.setFechaTicket("2026-05-01");
        existente.setClienteId(1L);

        TicketSoporteDTO dto = new TicketSoporteDTO();
        dto.setAsunto("Perfume dañado");
        dto.setMensaje("El frasco llegó roto");
        dto.setEstado("Abierto");
        dto.setFechaTicket("2026-05-02");
        dto.setClienteId(2L);

        ClienteResponse cliente = new ClienteResponse();
        cliente.setId(2L);
        cliente.setFechaRegistro("2026-02-15");
        cliente.setDireccionEnvio("Las Condes 303");

        when(clienteClient.obtenerCliente(2L, token))
                .thenReturn(cliente);

        when(tsRepo.findById(1L))
                .thenReturn(Optional.of(existente));

        when(tsRepo.save(any(TicketSoporte.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TicketSoporteResponse resultado = service.actualizar(1L, dto, token);

        assertNotNull(resultado);
        assertEquals("Perfume dañado", resultado.getAsunto());
        assertEquals("El frasco llegó roto", resultado.getMensaje());

        verify(tsRepo).findById(1L);
        verify(tsRepo).save(existente);
    }

    @Test
    void deberiaEliminarTicketPorId() {

        doNothing().when(tsRepo).deleteById(1L);

        service.eliminar(1L);

        verify(tsRepo).deleteById(1L);
    }
}
