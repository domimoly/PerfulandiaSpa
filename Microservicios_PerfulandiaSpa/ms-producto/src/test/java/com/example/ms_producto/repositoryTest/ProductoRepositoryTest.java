package com.example.ms_producto.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ms_producto.model.Producto;
import com.example.ms_producto.repository.ProductoRepository;

import java.util.Optional;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository repository;

    @Test
    void debeGuardarProducto() {
        Producto producto = new Producto(null, "Dior Sauvage",
                "Fragancia masculina 100ml", 130000.0, 40, 1L);

        Producto guardado = repository.save(producto);

        assertNotNull(guardado.getId());
        assertEquals("Dior Sauvage", guardado.getNombre());
        assertEquals("Fragancia masculina 100ml", guardado.getDescripcion());
        assertEquals(130000.0, guardado.getPrecio());
        assertEquals(40, guardado.getCantidad());
        assertEquals(1L, guardado.getCategoria());
    }

    @Test
    void debeBuscarProductoPorId() {
        Producto producto = new Producto(null, "Carolina Herrera Good Girl",
                "Perfume floral oriental para mujer 80ml", 115000.0, 50, 2L);
        Producto guardado = repository.save(producto);

        Optional<Producto> resultado = repository.findById(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Carolina Herrera Good Girl", resultado.get().getNombre());
        assertEquals(115000.0, resultado.get().getPrecio());
    }

    @Test
    void debeListarProductos() {
        repository.save(new Producto(null, "Dior Sauvage",
                "Fragancia masculina 100ml", 130000.0, 40, 1L));
        repository.save(new Producto(null, "Carolina Herrera Good Girl",
                "Perfume floral oriental para mujer 80ml", 115000.0, 50, 2L));

        List<Producto> resultado = repository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarProducto() {
        Producto producto = new Producto(null, "Calvin Klein CK One",
                "Aroma cítrico y fresco unisex 200ml", 45000.0, 30, 1L);
        Producto guardado = repository.save(producto);

        repository.deleteById(guardado.getId());

        Optional<Producto> resultado = repository.findById(guardado.getId());
        assertFalse(resultado.isPresent());
    }
}