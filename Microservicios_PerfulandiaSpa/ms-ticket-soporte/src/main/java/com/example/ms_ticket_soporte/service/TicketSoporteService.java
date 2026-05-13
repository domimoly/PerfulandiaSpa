package com.example.ms_ticket_soporte.service;

import org.springframework.stereotype.Service;

import com.example.ms_ticket_soporte.client.ClienteClient;
import com.example.ms_ticket_soporte.dto.TicketSoporteDTO;
import com.example.ms_ticket_soporte.dto.TicketSoporteResponse;
import com.example.ms_ticket_soporte.model.TicketSoporte;
import com.example.ms_ticket_soporte.repository.TicketSoporteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TicketSoporteService {

    private final TicketSoporteRepository tsRepo;
    private final ClienteClient clienteClient;

    public TicketSoporteResponse crear(TicketSoporteDTO dto, String token) {
        log.info("Crear ticket", keyValue("clienteId", dto.getClienteId()));

        var cliente = clienteClient.obtenerCliente(dto.getClienteId(), token);

        if (cliente == null) {
            throw new RuntimeException("Cliente no existe");
        }

        TicketSoporte ticket = tsRepo.save(
            new TicketSoporte(null, dto.getAsunto(), dto.getMensaje(),
                dto.getEstado() != null ? dto.getEstado() : "Abierto",
                dto.getFechaTicket(), dto.getClienteId())
        );

        return mapToResponse(ticket, token);
    }

    public List<TicketSoporteResponse> listar(String token) {
        log.info("Listar tickets");
        return tsRepo.findAll()
                .stream()
                .map(t -> mapToResponse(t, token))
                .toList();
    }

    public TicketSoporteResponse obtener(Long id, String token) {
        log.info("Obtener ticket", keyValue("id", id));

        TicketSoporte ticket = tsRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
                
        return mapToResponse(ticket, token);
    }

    public TicketSoporteResponse actualizar(Long id, TicketSoporteDTO dto, String token) {
        log.info("Actualizar ticket", keyValue("id", id));

        var cliente = clienteClient.obtenerCliente(dto.getClienteId(), token);

        if (cliente == null) {
            throw new RuntimeException("Cliente no existe");
        }

        TicketSoporte t = tsRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));

        t.setAsunto(dto.getAsunto());
        t.setMensaje(dto.getMensaje());
        t.setEstado(dto.getEstado());
        t.setFechaTicket(dto.getFechaTicket());
        t.setClienteId(dto.getClienteId());

        return mapToResponse(tsRepo.save(t), token);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar ticket", keyValue("id", id));
        tsRepo.deleteById(id);
    }

    private TicketSoporteResponse mapToResponse(TicketSoporte ticket, String token) {
        var cliente = clienteClient.obtenerCliente(ticket.getClienteId(), token);
        return TicketSoporteResponse.builder()
                .id(ticket.getId())
                .asunto(ticket.getAsunto())
                .mensaje(ticket.getMensaje())
                .estado(ticket.getEstado())
                .fechaTicket(ticket.getFechaTicket())
                .cliente(cliente)
                .build();
    }

}
