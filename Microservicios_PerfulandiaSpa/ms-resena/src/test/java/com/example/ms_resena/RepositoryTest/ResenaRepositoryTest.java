package com.example.ms_resena.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ms_resena.model.Resena;
import com.example.ms_resena.repository.ResenaRepository;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class ResenaRepositoryTest {

    @Autowired
    private ResenaRepository resenaRepository;

    @Test
    void debeGuardarResena() {
        Resena resena = new Resena(null, 5, "Excelente perfume", "2026-01-15", 1L, 1L);
        Resena guardada = resenaRepository.save(resena);

        assertNotNull(guardada.getId());
        assertEquals(5, guardada.getPuntuacion());
        assertEquals("Excelente perfume", guardada.getComentario());
        assertEquals(1L, guardada.getUsuarioId());
        assertEquals(1L, guardada.getProductoId());
    }

    @Test
    void debeBuscarResenaPorId() {
        Resena resena = new Resena(null, 4, "Muy buen aroma", "2026-02-01", 2L, 1L);
        Resena guardada = resenaRepository.save(resena);

        Optional<Resena> resultado = resenaRepository.findById(guardada.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Muy buen aroma", resultado.get().getComentario());
        assertEquals(2L, resultado.get().getUsuarioId());
    }

    @Test
    void debeListarResenas() {
        resenaRepository.save(new Resena(null, 5, "Increíble", "2026-01-01", 1L, 1L));
        resenaRepository.save(new Resena(null, 3, "Regular", "2026-02-01", 2L, 2L));

        List<Resena> resultado = resenaRepository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarResena() {
        Resena resena = new Resena(null, 2, "No me gustó", "2026-03-01", 3L, 1L);
        Resena guardada = resenaRepository.save(resena);

        resenaRepository.deleteById(guardada.getId());
        Optional<Resena> resultado = resenaRepository.findById(guardada.getId());

        assertFalse(resultado.isPresent());
    }

    @Test
    void debeActualizarResena() {
        Resena resena = new Resena(null, 3, "Comentario original", "2026-01-01", 1L, 1L);
        Resena guardada = resenaRepository.save(resena);

        guardada.setPuntuacion(5);
        guardada.setComentario("Comentario actualizado");
        resenaRepository.save(guardada);

        Optional<Resena> resultado = resenaRepository.findById(guardada.getId());
        assertTrue(resultado.isPresent());
        assertEquals(5, resultado.get().getPuntuacion());
        assertEquals("Comentario actualizado", resultado.get().getComentario());
    }

    @Test
    void debeBuscarResenasPorProducto() {
        resenaRepository.save(new Resena(null, 5, "Buenísimo", "2026-01-01", 1L, 10L));
        resenaRepository.save(new Resena(null, 4, "Muy bueno", "2026-01-02", 2L, 10L));

        List<Resena> resultado = resenaRepository.findByProductoId(10L);

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }
}
