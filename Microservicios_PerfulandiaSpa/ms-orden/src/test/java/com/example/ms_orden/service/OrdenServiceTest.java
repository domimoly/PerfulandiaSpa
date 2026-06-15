package com.example.ms_orden.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_orden.dto.OrdenDTO;
import com.example.ms_orden.model.Orden;
import com.example.ms_orden.repository.OrdenRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class OrdenServiceTest {

    @Mock
    private OrdenRepository repo;

    @InjectMocks
    private OrdenService service;

    @Test
    void deberiaRetornarOrdenCuandoExiste() {
        Orden orden = new Orden(1L, 1, "2026-03-20", "2026-03-23", 115000, 0);
        when(repo.findById(1L)).thenReturn(Optional.of(orden));

        Orden resultado = service.obtener(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(1, resultado.getNumeroOrden());
        assertEquals("2026-03-20", resultado.getFechaCreacion());
        assertEquals("2026-03-23", resultado.getFechaRecibida());
        assertEquals(115000, resultado.getTotal());
        assertEquals(0, resultado.getDescuentoAplicado());
        verify(repo).findById(1L);

    }

    @Test
    void deberiaLanzarExcepcionCuandoOrdenNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
        EntityNotFoundException.class,
        () -> service.obtener(99L)
        );

        assertEquals("Orden no encontrada", ex.getMessage());
        verify(repo).findById(99L);
    }

    @Test
    void deberiaRetornarListaOrdenes() {
        Orden orden = new Orden(1L, 1, "2026-03-20", "2026-03-23", 115000, 0);
        when(repo.findAll()).thenReturn(List.of(orden));

        List<Orden> resultado = service.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getNumeroOrden());
        verify(repo).findAll();
    }

    @Test
    void deberiaCrearOrdenCorrectamente() {
        OrdenDTO dto = new OrdenDTO();
        dto.setNumeroOrden(1);
        dto.setFechaCreacion("2026-03-20");
        dto.setFechaRecibida("2026-03-23");
        dto.setTotal(115000);
        dto.setDescuentoAplicado(0);

        Orden ordenGuardada = new Orden(1L, 1, "2026-03-20", "2026-03-23", 115000, 0);
        when(repo.save(any(Orden.class))).thenReturn(ordenGuardada);

        Orden resultado = service.crear(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(1, resultado.getNumeroOrden());
        assertEquals("2026-03-20", resultado.getFechaCreacion());
        assertEquals("2026-03-23", resultado.getFechaRecibida());
        assertEquals(115000, resultado.getTotal());
        assertEquals(0, resultado.getDescuentoAplicado());
        verify(repo).save(any(Orden.class));
    }

    @Test
    void deberiaActualizarOrdenCorrectamente() {
        Orden existente = new Orden(1L, 3, "2026-04-01", "2026-04-05", 75000, 5000);

        OrdenDTO dto = new OrdenDTO();
        dto.setNumeroOrden(4);
        dto.setFechaCreacion("2026-04-06");
        dto.setFechaRecibida("2026-04-10");
        dto.setTotal(80000);
        dto.setDescuentoAplicado(6000);

        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(any(Orden.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Orden resultado = service.actualizar(1L, dto);

        assertEquals(1L, resultado.getId());
        assertEquals(4, resultado.getNumeroOrden());
        assertEquals("2026-04-06", resultado.getFechaCreacion());
        assertEquals("2026-04-10", resultado.getFechaRecibida());
        assertEquals(80000, resultado.getTotal());
        assertEquals(6000, resultado.getDescuentoAplicado());
        verify(repo).findById(1L);
        verify(repo).save(existente);

    }

    @Test
    void deberiaEliminarOrdenPorId() {
        doNothing().when(repo).deleteById(1L);

        service.eliminar(1L);

        verify(repo).deleteById(1L);
    }
}



