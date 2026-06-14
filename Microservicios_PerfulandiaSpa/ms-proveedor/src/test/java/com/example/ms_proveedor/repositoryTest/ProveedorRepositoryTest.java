package com.example.ms_proveedor.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ms_proveedor.model.Proveedor;
import com.example.ms_proveedor.repository.ProveedorRepository;

import java.util.Optional;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class ProveedorRepositoryTest {

    @Autowired
    private ProveedorRepository repository;

    @Test
    void debeGuardarProveedor() {
        Proveedor proveedor = new Proveedor(null, "Perfumeria Italiana",
                "contacto@perfumeriaitaliana.cl", "+56975842043",
                "Av. Italia 1439", 1L, 1L);

        Proveedor guardado = repository.save(proveedor);

        assertNotNull(guardado.getId());
        assertEquals("Perfumeria Italiana", guardado.getNombre());
        assertEquals("contacto@perfumeriaitaliana.cl", guardado.getEmail());
        assertEquals(1L, guardado.getSucursal());
        assertEquals(1L, guardado.getProducto());
    }

    @Test
    void debeBuscarProveedorPorId() {
        Proveedor proveedor = new Proveedor(null, "Perfumarte",
                "importaciones@perfumarte.cl", "+56911223344",
                "Libertador 780, Concepción", 2L, 2L);
        Proveedor guardado = repository.save(proveedor);

        Optional<Proveedor> resultado = repository.findById(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Perfumarte", resultado.get().getNombre());
        assertEquals(2L, resultado.get().getSucursal());
    }

    @Test
    void debeListarProveedores() {
        repository.save(new Proveedor(null, "Perfumeria Italiana",
                "contacto@perfumeriaitaliana.cl", "+56975842043",
                "Av. Italia 1439", 1L, 1L));
        repository.save(new Proveedor(null, "Perfumarte",
                "importaciones@perfumarte.cl", "+56911223344",
                "Libertador 780, Concepción", 2L, 2L));

        List<Proveedor> resultado = repository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarProveedor() {
        Proveedor proveedor = new Proveedor(null, "Aroma di Vita",
                "ventas@divita.cl", "+56987654321",
                "Av. Borgoño 14580, Viña del Mar", 3L, 3L);
        Proveedor guardado = repository.save(proveedor);

        repository.deleteById(guardado.getId());

        Optional<Proveedor> resultado = repository.findById(guardado.getId());
        assertFalse(resultado.isPresent());
    }
}