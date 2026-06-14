package com.example.ms_cupon_descuento.serviceTest;

import com.example.ms_cupon_descuento.client.CategoriaClient;
import com.example.ms_cupon_descuento.dto.CategoriaResponse;
import com.example.ms_cupon_descuento.dto.CuponDescuentoDTO;
import com.example.ms_cupon_descuento.dto.CuponResponse;
import com.example.ms_cupon_descuento.model.CuponDescuento;
import com.example.ms_cupon_descuento.repository.CuponDescuentoRepository;
import com.example.ms_cupon_descuento.service.CuponDescuentoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuponDescuentoServiceTest {

    @Mock
    private CuponDescuentoRepository cuponRepo;

    @Mock
    private CategoriaClient categoriaClient;

    @InjectMocks
    private CuponDescuentoService service;

    private final String token = "Bearer test";

    @Test
    void deberiaRetornarCuponCuandoExiste() {

        // Arrange
        CuponDescuento cupon = new CuponDescuento();
        cupon.setId(1L);
        cupon.setCategoria(1L);
        cupon.setCodigo("HOMBRE20");
        cupon.setPorcentajeDescuento(20.0);
        cupon.setFechaVencimiento(LocalDate.of(2026, 12, 31));
        cupon.setActivo(true);

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        when(cuponRepo.findById(1L)).thenReturn(Optional.of(cupon));
        when(categoriaClient.obtenerCategoria(anyLong(), anyString())).thenReturn(categoria);

        // Act
        CuponResponse resultado = service.obtener(1L, token);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("HOMBRE20", resultado.getCodigo());

        verify(cuponRepo).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoCuponNoExiste() {

        // Arrange
        when(cuponRepo.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> service.obtener(99L, token)
        );

        assertEquals("Cupón no encontrado", ex.getMessage());

        verify(cuponRepo).findById(99L);
    }

    @Test
    void deberiaRetornarListaCupones() {

        // Arrange
        CuponDescuento cupon = new CuponDescuento();
        cupon.setId(1L);
        cupon.setCategoria(1L);
        cupon.setCodigo("HOMBRE20");
        cupon.setPorcentajeDescuento(20.0);
        cupon.setFechaVencimiento(LocalDate.of(2026, 12, 31));
        cupon.setActivo(true);

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        when(cuponRepo.findAll()).thenReturn(List.of(cupon));
        when(categoriaClient.obtenerCategoria(anyLong(), anyString())).thenReturn(categoria);

        // Act
        List<CuponResponse> resultado = service.listar(token);

        // Assert
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("HOMBRE20", resultado.get(0).getCodigo());

        verify(cuponRepo).findAll();
    }

    @Test
    void deberiaCrearCuponCorrectamente() {

        // Arrange
        CuponDescuentoDTO dto = new CuponDescuentoDTO();
        dto.setCategoria(1L);
        dto.setCodigo("HOMBRE20");
        dto.setPorcentajeDescuento(20.0);
        dto.setFechaVencimiento(LocalDate.of(2026, 12, 31));
        dto.setActivo(true);

        CuponDescuento cuponGuardado = new CuponDescuento();
        cuponGuardado.setId(1L);
        cuponGuardado.setCategoria(dto.getCategoria());
        cuponGuardado.setCodigo(dto.getCodigo());
        cuponGuardado.setPorcentajeDescuento(dto.getPorcentajeDescuento());
        cuponGuardado.setFechaVencimiento(dto.getFechaVencimiento());
        cuponGuardado.setActivo(dto.getActivo());

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        when(categoriaClient.obtenerCategoria(anyLong(), anyString())).thenReturn(categoria);
        when(cuponRepo.save(any(CuponDescuento.class))).thenReturn(cuponGuardado);

        // Act
        CuponResponse resultado = service.crear(dto, token);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("HOMBRE20", resultado.getCodigo());

        verify(cuponRepo).save(any(CuponDescuento.class));
    }

    @Test
    void deberiaActualizarCuponCorrectamente() {

        // Arrange
        CuponDescuento existente = new CuponDescuento();
        existente.setId(1L);
        existente.setCategoria(1L);
        existente.setCodigo("VIEJO10");
        existente.setPorcentajeDescuento(10.0);
        existente.setFechaVencimiento(LocalDate.of(2026, 6, 30));
        existente.setActivo(true);

        CuponDescuentoDTO dto = new CuponDescuentoDTO();
        dto.setCategoria(1L);
        dto.setCodigo("NUEVO30");
        dto.setPorcentajeDescuento(30.0);
        dto.setFechaVencimiento(LocalDate.of(2026, 12, 31));
        dto.setActivo(true);

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        when(categoriaClient.obtenerCategoria(anyLong(), anyString())).thenReturn(categoria);
        when(cuponRepo.findById(1L)).thenReturn(Optional.of(existente));
        when(cuponRepo.save(any(CuponDescuento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CuponResponse resultado = service.actualizar(1L, dto, token);

        // Assert
        assertEquals("NUEVO30", resultado.getCodigo());
        assertEquals(30.0, resultado.getPorcentajeDescuento());

        verify(cuponRepo).findById(1L);
        verify(cuponRepo).save(existente);
    }

    @Test
    void deberiaEliminarCuponPorId() {

        // Arrange
        doNothing().when(cuponRepo).deleteById(1L);

        // Act
        service.eliminar(1L);

        // Assert
        verify(cuponRepo).deleteById(1L);
    }
}