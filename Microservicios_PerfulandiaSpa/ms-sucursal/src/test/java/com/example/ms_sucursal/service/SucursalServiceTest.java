package com.example.ms_sucursal.service;

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

import com.example.ms_sucursal.dto.SucursalDTO;
import com.example.ms_sucursal.model.Sucursal;
import com.example.ms_sucursal.repository.SucursalRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

    @Mock
    private SucursalRepository repo;

    @InjectMocks
    private SucursalService service;

    @Test
    void deberiaRetornarSucursalCuandoExiste() {
        Sucursal sucursal = new Sucursal(1L, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias");
        when(repo.findById(1L)).thenReturn(Optional.of(sucursal));

        Sucursal resultado = service.obtener(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Barrio Meiggs", resultado.getNombre());
        assertEquals("Barrio Meiggs, Santiago", resultado.getDireccion());
        assertEquals("Lun-Sab 9:00-20:00", resultado.getHorarioApertura());
        assertEquals("Devolucion con boleta en 30 dias", resultado.getPoliticas());
        verify(repo).findById(1L);

    }

    @Test
    void deberiaLanzarExcepcionCuandoSucursalNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
        EntityNotFoundException.class,
        () -> service.obtener(99L)
        );

        assertEquals("Sucursal no encontrada", ex.getMessage());
        verify(repo).findById(99L);
    }

    @Test
    void deberiaRetornarListaSucursales() {
        Sucursal sucursal = new Sucursal(1L, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias");
        when(repo.findAll()).thenReturn(List.of(sucursal));

        List<Sucursal> resultado = service.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Barrio Meiggs", resultado.get(0).getNombre());
        verify(repo).findAll();
    }

    @Test
    void deberiaCrearSucursalCorrectamente() {
        SucursalDTO dto = new SucursalDTO();
        dto.setNombre("Barrio Meiggs");
        dto.setDireccion("Barrio Meiggs, Santiago");
        dto.setHorarioApertura("Lun-Sab 9:00-20:00");
        dto.setPoliticas("Devolucion con boleta en 30 dias");

        Sucursal sucursalGuardada = new Sucursal(1L, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias");
        when(repo.save(any(Sucursal.class))).thenReturn(sucursalGuardada);

        Sucursal resultado = service.crear(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Barrio Meiggs", resultado.getNombre());
        assertEquals("Barrio Meiggs, Santiago", resultado.getDireccion());
        assertEquals("Lun-Sab 9:00-20:00", resultado.getHorarioApertura());
        assertEquals("Devolucion con boleta en 30 dias", resultado.getPoliticas());
        verify(repo).save(any(Sucursal.class));
    }

    @Test
    void deberiaActualizarSucursalCorrectamente() {
        Sucursal existente = new Sucursal(1L, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias");

        SucursalDTO dto = new SucursalDTO();
        dto.setNombre("Concepcion");
        dto.setDireccion("Av. OHiggins 123, Concepcion");
        dto.setHorarioApertura("Lun-Sab 10:00-19:00");
        dto.setPoliticas("Devolucion con boleta en 30 dias");

        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(any(Sucursal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Sucursal resultado = service.actualizar(1L, dto);

        assertEquals(1L, resultado.getId());
        assertEquals("Concepcion", resultado.getNombre());
        assertEquals("Av. OHiggins 123, Concepcion", resultado.getDireccion());
        assertEquals("Lun-Sab 10:00-19:00", resultado.getHorarioApertura());
        assertEquals("Devolucion con boleta en 30 dias", resultado.getPoliticas());
        verify(repo).findById(1L);
        verify(repo).save(existente);

    }

    @Test
    void deberiaEliminarSucursalPorId() {
        doNothing().when(repo).deleteById(1L);

        service.eliminar(1L);

        verify(repo).deleteById(1L);
    }
}
