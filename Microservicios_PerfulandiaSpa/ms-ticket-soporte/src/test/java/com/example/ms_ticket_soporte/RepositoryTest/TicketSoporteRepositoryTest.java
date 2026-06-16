package com.example.ms_ticket_soporte.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ms_ticket_soporte.model.TicketSoporte;
import com.example.ms_ticket_soporte.repository.TicketSoporteRepository;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class TicketSoporteRepositoryTest {

    @Autowired
    private TicketSoporteRepository ticketRepository;

    @Test
    void debeGuardarTicket() {
        TicketSoporte ticket = new TicketSoporte(null, "Pedido perdido",
                "Mi pedido no llegó", "Abierto", "2026-05-01", 1L);
        TicketSoporte guardado = ticketRepository.save(ticket);

        assertNotNull(guardado.getId());
        assertEquals("Pedido perdido", guardado.getAsunto());
        assertEquals("Mi pedido no llegó", guardado.getMensaje());
        assertEquals("Abierto", guardado.getEstado());
        assertEquals(1L, guardado.getClienteId());
    }

    @Test
    void debeBuscarTicketPorId() {
        TicketSoporte ticket = new TicketSoporte(null, "Perfume dañado",
                "El frasco llegó roto", "En proceso", "2026-05-02", 2L);
        TicketSoporte guardado = ticketRepository.save(ticket);

        Optional<TicketSoporte> resultado = ticketRepository.findById(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Perfume dañado", resultado.get().getAsunto());
        assertEquals(2L, resultado.get().getClienteId());
    }

    @Test
    void debeListarTickets() {
        ticketRepository.save(new TicketSoporte(null, "Asunto 1",
                "Mensaje 1", "Abierto", "2026-05-01", 1L));
        ticketRepository.save(new TicketSoporte(null, "Asunto 2",
                "Mensaje 2", "Cerrado", "2026-05-02", 2L));

        List<TicketSoporte> resultado = ticketRepository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarTicket() {
        TicketSoporte ticket = new TicketSoporte(null, "Cobro incorrecto",
                "Me cobraron de más", "Abierto", "2026-05-03", 3L);
        TicketSoporte guardado = ticketRepository.save(ticket);

        ticketRepository.deleteById(guardado.getId());
        Optional<TicketSoporte> resultado = ticketRepository.findById(guardado.getId());

        assertFalse(resultado.isPresent());
    }

    @Test
    void debeActualizarTicket() {
        TicketSoporte ticket = new TicketSoporte(null, "Asunto original",
                "Mensaje original", "Abierto", "2026-05-01", 1L);
        TicketSoporte guardado = ticketRepository.save(ticket);

        guardado.setEstado("Cerrado");
        guardado.setAsunto("Asunto actualizado");
        ticketRepository.save(guardado);

        Optional<TicketSoporte> resultado = ticketRepository.findById(guardado.getId());
        assertTrue(resultado.isPresent());
        assertEquals("Cerrado", resultado.get().getEstado());
        assertEquals("Asunto actualizado", resultado.get().getAsunto());
    }
}
