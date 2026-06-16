package com.example.ms_orden.repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ms_orden.model.Orden;

@DataJpaTest
@ActiveProfiles("test")
class OrdenRepositoryTest {

    @Autowired
    private OrdenRepository repository;

    @Test
    void debeGuardarOrden() {
        Orden orden = new Orden(null, 1, "2026-03-20", "2026-03-23", 115000, 0);

        Orden guardada = repository.save(orden);

        assertNotNull(guardada.getId());
        assertEquals(1, guardada.getNumeroOrden());
        assertEquals("2026-03-20", guardada.getFechaCreacion());
        assertEquals("2026-03-23", guardada.getFechaRecibida());
        assertEquals(115000, guardada.getTotal());
        assertEquals(0, guardada.getDescuentoAplicado());
    }

    @Test
    void debeBuscarAutorPorId() {
        Orden orden = new Orden(null, 1, "2026-03-20", "2026-03-23", 115000, 0);
        Orden guardada = repository.save(orden);

        Optional<Orden> resultado = repository.findById(guardada.getId());

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getNumeroOrden());
        assertEquals("2026-03-20", resultado.get().getFechaCreacion());
        assertEquals("2026-03-23", resultado.get().getFechaRecibida());
        assertEquals(115000, resultado.get().getTotal());
        assertEquals(0, resultado.get().getDescuentoAplicado());
    }

    @Test
    void debeListarOrdenes() {
        repository.save(new Orden(null, 1, "2026-03-20", "2026-03-23", 115000, 0));
        repository.save(new Orden(null, 2, "2026-03-21", "2026-03-24", 125000, 0));

        List<Orden> resultado = repository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarOrden() {
        Orden orden = new Orden(null, 3, "2026-03-22", "2026-03-25", 135000, 0);
        Orden guardada = repository.save(orden);

        repository.deleteById(guardada.getId());

        Optional<Orden> resultado = repository.findById(guardada.getId());
        assertFalse(resultado.isPresent());
    }
}
