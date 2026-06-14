package com.example.ms_categoria.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ms_categoria.model.Categoria;
import com.example.ms_categoria.repository.CategoriaRepository;

import java.util.Optional;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository repository;

    @Test
    void debeGuardarCategoria() {
        Categoria categoria = new Categoria(null, "Perfume Hombre",
                "Fragancias masculinas");

        Categoria guardada = repository.save(categoria);

        assertNotNull(guardada.getId());
        assertEquals("Perfume Hombre", guardada.getNombre());
        assertEquals("Fragancias masculinas", guardada.getDescripcion());
    }

    @Test
    void debeBuscarCategoriaPorId() {
        Categoria categoria = new Categoria(null, "Perfume Mujer",
                "Fragancias femeninas");
        Categoria guardada = repository.save(categoria);

        Optional<Categoria> resultado = repository.findById(guardada.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Perfume Mujer", resultado.get().getNombre());
        assertEquals("Fragancias femeninas", resultado.get().getDescripcion());
    }

    @Test
    void debeListarCategorias() {
        repository.save(new Categoria(null, "Perfume Hombre", "Fragancias masculinas"));
        repository.save(new Categoria(null, "Perfume Mujer", "Fragancias femeninas"));

        List<Categoria> resultado = repository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarCategoria() {
        Categoria categoria = new Categoria(null, "Perfume Niños",
                "Fragancias suaves para niños");
        Categoria guardada = repository.save(categoria);

        repository.deleteById(guardada.getId());

        Optional<Categoria> resultado = repository.findById(guardada.getId());
        assertFalse(resultado.isPresent());
    }
}