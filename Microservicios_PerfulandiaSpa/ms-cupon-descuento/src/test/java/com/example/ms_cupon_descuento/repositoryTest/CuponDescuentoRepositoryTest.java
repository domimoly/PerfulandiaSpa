package com.example.ms_cupon_descuento.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ms_cupon_descuento.model.CuponDescuento;
import com.example.ms_cupon_descuento.repository.CuponDescuentoRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class CuponDescuentoRepositoryTest {

    @Autowired
    private CuponDescuentoRepository repository;

    @Test
    void debeGuardarCupon() {
        CuponDescuento cupon = new CuponDescuento(null, 1L, "HOMBRE20",
                20.0, LocalDate.of(2026, 12, 31), true);

        CuponDescuento guardado = repository.save(cupon);

        assertNotNull(guardado.getId());
        assertEquals(1L, guardado.getCategoria());
        assertEquals("HOMBRE20", guardado.getCodigo());
        assertEquals(20.0, guardado.getPorcentajeDescuento());
        assertEquals(LocalDate.of(2026, 12, 31), guardado.getFechaVencimiento());
        assertTrue(guardado.getActivo());
    }

    @Test
    void debeBuscarCuponPorId() {
        CuponDescuento cupon = new CuponDescuento(null, 2L, "MUJER15",
                15.0, LocalDate.of(2026, 12, 31), true);
        CuponDescuento guardado = repository.save(cupon);

        Optional<CuponDescuento> resultado = repository.findById(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("MUJER15", resultado.get().getCodigo());
        assertEquals(15.0, resultado.get().getPorcentajeDescuento());
    }

    @Test
    void debeListarCupones() {
        repository.save(new CuponDescuento(null, 1L, "HOMBRE20",
                20.0, LocalDate.of(2026, 12, 31), true));
        repository.save(new CuponDescuento(null, 2L, "MUJER15",
                15.0, LocalDate.of(2026, 12, 31), true));

        List<CuponDescuento> resultado = repository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarCupon() {
        CuponDescuento cupon = new CuponDescuento(null, 1L, "VERANO10",
                10.0, LocalDate.of(2026, 8, 31), true);
        CuponDescuento guardado = repository.save(cupon);

        repository.deleteById(guardado.getId());

        Optional<CuponDescuento> resultado = repository.findById(guardado.getId());
        assertFalse(resultado.isPresent());
    }
}