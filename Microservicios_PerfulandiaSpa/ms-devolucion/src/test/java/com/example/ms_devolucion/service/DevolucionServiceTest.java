package com.example.ms_devolucion.service;

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

import com.example.ms_devolucion.dto.DevolucionDTO;
import com.example.ms_devolucion.model.Devolucion;
import com.example.ms_devolucion.repository.DevolucionRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class DevolucionServiceTest {

    @Mock
    private DevolucionRepository repo;

    @InjectMocks
    private DevolucionService service;

    @Test
    void deberiaRetornarDevolucionCuandoExiste() {
        Devolucion devolucion = new Devolucion(1L, "2026-05-09", "Producto defectuoso", "Pendiente");
        when(repo.findById(1L)).thenReturn(Optional.of(devolucion));

        Devolucion resultado = service.obtener(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("2026-05-09", resultado.getFechaDevolucion());
        assertEquals("Producto defectuoso", resultado.getMotivo());
        assertEquals("Pendiente", resultado.getEstado());
        verify(repo).findById(1L);

    }

    @Test
    void deberiaLanzarExcepcionCuandoDevolucionNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
        EntityNotFoundException.class,
        () -> service.obtener(99L)
        );

        assertEquals("Devolución no encontrada", ex.getMessage());
        verify(repo).findById(99L);
    }

    @Test
    void deberiaRetornarListaDevoluciones() {
        Devolucion devolucion = new Devolucion(1L, "2026-05-09", "Producto defectuoso", "Pendiente");
        when(repo.findAll()).thenReturn(List.of(devolucion));

        List<Devolucion> resultado = service.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("2026-05-09", resultado.get(0).getFechaDevolucion());
        verify(repo).findAll();
    }

    @Test
    void deberiaCrearDevolucionCorrectamente() {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setFechaDevolucion("2026-05-10");
        dto.setMotivo("Producto no deseado");
        dto.setEstado("Aprobada");

        Devolucion devolucionGuardada = new Devolucion(1L, "2026-05-10", "Producto no deseado", "Aprobada");
        when(repo.save(any(Devolucion.class))).thenReturn(devolucionGuardada);

        Devolucion resultado = service.crear(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("2026-05-10", resultado.getFechaDevolucion());
        assertEquals("Producto no deseado", resultado.getMotivo());
        assertEquals("Aprobada", resultado.getEstado());
        verify(repo).save(any(Devolucion.class));
    }

    @Test
    void deberiaActualizarDevolucionCorrectamente() {
        Devolucion existente = new Devolucion(1L, "2026-05-15", "Producto defectuoso", "Rechazada");

        DevolucionDTO dto = new DevolucionDTO();
        dto.setFechaDevolucion("2026-05-20");
        dto.setMotivo("Producto defectuoso");
        dto.setEstado("Aprobada");

        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(any(Devolucion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Devolucion resultado = service.actualizar(1L, dto);

        assertEquals(1L, resultado.getId());
        assertEquals("2026-05-20", resultado.getFechaDevolucion());
        assertEquals("Producto defectuoso", resultado.getMotivo());
        assertEquals("Aprobada", resultado.getEstado());
        verify(repo).findById(1L);
        verify(repo).save(existente);

    }

    @Test
    void deberiaEliminarDevolucionPorId() {
        doNothing().when(repo).deleteById(1L);

        service.eliminar(1L);

        verify(repo).deleteById(1L);
    }
}


