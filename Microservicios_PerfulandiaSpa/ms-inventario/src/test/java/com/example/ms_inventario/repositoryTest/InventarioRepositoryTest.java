package com.example.ms_inventario.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.repository.InventarioRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class InventarioRepositoryTest {

    @Autowired
    private InventarioRepository repository;

    @Test
    void debeGuardarInventario() {
        Inventario inventario = new Inventario(null, 1L, 1L, 50, 10,
                LocalDate.of(2025, 1, 15), 1L);

        Inventario guardado = repository.save(inventario);

        assertNotNull(guardado.getId());
        assertEquals(1L, guardado.getProducto());
        assertEquals(1L, guardado.getSucursal());
        assertEquals(50, guardado.getCantidad());
        assertEquals(10, guardado.getStockMinimo());
        assertEquals(LocalDate.of(2025, 1, 15), guardado.getFechaInventario());
        assertEquals(1L, guardado.getProveedor());
    }

    @Test
    void debeBuscarInventarioPorId() {
        Inventario inventario = new Inventario(null, 2L, 2L, 30, 5,
                LocalDate.of(2025, 2, 10), 2L);
        Inventario guardado = repository.save(inventario);

        Optional<Inventario> resultado = repository.findById(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals(30, resultado.get().getCantidad());
        assertEquals(2L, resultado.get().getProducto());
    }

    @Test
    void debeListarInventarios() {
        repository.save(new Inventario(null, 1L, 1L, 50, 10,
                LocalDate.of(2025, 1, 15), 1L));
        repository.save(new Inventario(null, 2L, 2L, 30, 5,
                LocalDate.of(2025, 2, 10), 2L));

        List<Inventario> resultado = repository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarInventario() {
        Inventario inventario = new Inventario(null, 3L, 3L, 80, 15,
                LocalDate.of(2025, 3, 5), 3L);
        Inventario guardado = repository.save(inventario);

        repository.deleteById(guardado.getId());

        Optional<Inventario> resultado = repository.findById(guardado.getId());
        assertFalse(resultado.isPresent());
    }
}