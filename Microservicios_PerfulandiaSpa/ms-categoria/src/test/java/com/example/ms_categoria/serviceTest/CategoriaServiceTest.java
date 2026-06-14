package com.example.ms_categoria.serviceTest;

import com.example.ms_categoria.dto.CategoriaDTO;
import com.example.ms_categoria.model.Categoria;
import com.example.ms_categoria.repository.CategoriaRepository;
import com.example.ms_categoria.service.CategoriaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepo;

    @InjectMocks
    private CategoriaService service;

    @Test
    void deberiaRetornarCategoriaCuandoExiste() {

        // Arrange
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        when(categoriaRepo.findById(1L)).thenReturn(Optional.of(categoria));

        // Act
        Categoria resultado = service.obtener(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Perfume Hombre", resultado.getNombre());

        verify(categoriaRepo).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoCategoriaNoExiste() {

        // Arrange
        when(categoriaRepo.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> service.obtener(99L)
        );

        assertEquals("Categoria no encontrada", ex.getMessage());

        verify(categoriaRepo).findById(99L);
    }

    @Test
    void deberiaRetornarListaCategorias() {

        // Arrange
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        when(categoriaRepo.findAll()).thenReturn(List.of(categoria));

        // Act
        List<Categoria> resultado = service.listar();

        // Assert
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Perfume Hombre", resultado.get(0).getNombre());

        verify(categoriaRepo).findAll();
    }

    @Test
    void deberiaCrearCategoriaCorrectamente() {

        // Arrange
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Perfume Mujer");
        dto.setDescripcion("Fragancias femeninas");

        Categoria categoriaGuardada = new Categoria();
        categoriaGuardada.setId(1L);
        categoriaGuardada.setNombre(dto.getNombre());
        categoriaGuardada.setDescripcion(dto.getDescripcion());

        when(categoriaRepo.save(any(Categoria.class))).thenReturn(categoriaGuardada);

        // Act
        Categoria resultado = service.crear(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Perfume Mujer", resultado.getNombre());

        verify(categoriaRepo).save(any(Categoria.class));
    }

    @Test
    void deberiaActualizarCategoriaCorrectamente() {

        // Arrange
        Categoria existente = new Categoria();
        existente.setId(1L);
        existente.setNombre("Categoria vieja");
        existente.setDescripcion("Desc vieja");

        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Categoria nueva");
        dto.setDescripcion("Desc nueva");

        when(categoriaRepo.findById(1L)).thenReturn(Optional.of(existente));
        when(categoriaRepo.save(any(Categoria.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Categoria resultado = service.actualizar(1L, dto);

        // Assert
        assertEquals("Categoria nueva", resultado.getNombre());
        assertEquals("Desc nueva", resultado.getDescripcion());

        verify(categoriaRepo).findById(1L);
        verify(categoriaRepo).save(existente);
    }

    @Test
    void deberiaEliminarCategoriaPorId() {

        // Arrange
        doNothing().when(categoriaRepo).deleteById(1L);

        // Act
        service.eliminar(1L);

        // Assert
        verify(categoriaRepo).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlActualizarCategoriaInexistente() {

        // Arrange
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Categoria inexistente");
        dto.setDescripcion("Desc test");

        when(categoriaRepo.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                EntityNotFoundException.class,
                () -> service.actualizar(99L, dto)
        );

        verify(categoriaRepo).findById(99L);
        verify(categoriaRepo, never()).save(any());
    }
}