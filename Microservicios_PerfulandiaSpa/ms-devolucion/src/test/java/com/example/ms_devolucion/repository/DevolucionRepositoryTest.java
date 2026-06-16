package com.example.ms_devolucion.repository;

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

import com.example.ms_devolucion.model.Devolucion;

@DataJpaTest
@ActiveProfiles("test")
class DevolucionRepositoryTest {

    @Autowired
    private DevolucionRepository repository;

    @Test
    void debeGuardarDevolucion() {
        Devolucion devolucion = new Devolucion(null, "2026-05-09", "Producto defectuoso", "Pendiente");

        Devolucion guardado = repository.save(devolucion);

        assertNotNull(guardado.getId());
        assertEquals("2026-05-09", guardado.getFechaDevolucion());
        assertEquals("Producto defectuoso", guardado.getMotivo());
        assertEquals("Pendiente", guardado.getEstado());
    }

    @Test
    void debeBuscarDevolucionPorId() {
        Devolucion devolucion = new Devolucion(null, "2026-05-09", "Producto defectuoso", "Pendiente");
        Devolucion guardado = repository.save(devolucion);

        Optional<Devolucion> resultado = repository.findById(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("2026-05-09", resultado.get().getFechaDevolucion());
        assertEquals("Producto defectuoso", resultado.get().getMotivo());
        assertEquals("Pendiente", resultado.get().getEstado());
    }

    @Test
    void debeListarDevoluciones() {
        repository.save(new Devolucion(null, "2026-05-09", "Producto defectuoso", "Pendiente"));
        repository.save(new Devolucion(null, "2026-05-10", "Producto dañado", "Aprobada"));

        List<Devolucion> resultado = repository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarDevolucion() {
        Devolucion devolucion = new Devolucion(null, "2026-05-09", "Producto defectuoso", "Pendiente");
        Devolucion guardado = repository.save(devolucion);

        repository.deleteById(guardado.getId());

        Optional<Devolucion> resultado = repository.findById(guardado.getId());
        assertFalse(resultado.isPresent());
    }
}

