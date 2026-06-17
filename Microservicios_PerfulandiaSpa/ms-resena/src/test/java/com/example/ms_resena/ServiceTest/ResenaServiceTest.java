package com.example.ms_resena.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_resena.client.UsuarioClient;
import com.example.ms_resena.dto.ResenaDTO;
import com.example.ms_resena.dto.ResenaResponse;
import com.example.ms_resena.dto.UsuarioResponse;
import com.example.ms_resena.model.Resena;
import com.example.ms_resena.repository.ResenaRepository;
import com.example.ms_resena.service.ResenaService;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ResenaServiceTest {

    @Mock
    private ResenaRepository repo;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private ResenaService resenaService;

    private static final String TOKEN = "Bearer test-token";
    private UsuarioResponse usuario;
    private Resena resena;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioResponse();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan@perfulandia.cl");

        resena = new Resena(1L, 5, "Excelente perfume", "2026-01-15", 1L);
    }

    // --- CREAR ---

    @Test
    void crear_conUsuarioExistente_retornaResenaResponse() {
        // Given
        ResenaDTO dto = new ResenaDTO();
        dto.setPuntuacion(5);
        dto.setComentario("Excelente perfume");
        dto.setFechaResena("2026-01-15");
        dto.setUsuarioId(1L);

        when(usuarioClient.obtenerUsuario(1L, TOKEN)).thenReturn(usuario);
        when(repo.save(any(Resena.class))).thenReturn(resena);

        // When
        ResenaResponse response = resenaService.crear(dto, TOKEN);

        // Then
        assertNotNull(response);
        assertEquals(5, response.getPuntuacion());
        assertEquals("Excelente perfume", response.getComentario());
        verify(repo).save(any(Resena.class));
    }

    @Test
    void crear_usuarioNoExiste_lanzaExcepcion() {
        // Given
        ResenaDTO dto = new ResenaDTO();
        dto.setPuntuacion(4);
        dto.setComentario("Buen perfume");
        dto.setFechaResena("2026-01-20");
        dto.setUsuarioId(99L);

        when(usuarioClient.obtenerUsuario(99L, TOKEN)).thenReturn(null);

        // When / Then
        assertThrows(RuntimeException.class, () -> resenaService.crear(dto, TOKEN));
        verify(repo, never()).save(any());
    }

    // --- LISTAR ---

    @Test
    void listar_retornaListaDeResenas() {
        // Given
        when(repo.findAll()).thenReturn(List.of(resena));
        when(usuarioClient.obtenerUsuario(1L, TOKEN)).thenReturn(usuario);

        // When
        List<ResenaResponse> resultado = resenaService.listar(TOKEN);

        // Then
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(5, resultado.get(0).getPuntuacion());
    }

    @Test
    void listar_sinResenas_retornaListaVacia() {
        // Given
        when(repo.findAll()).thenReturn(List.of());

        // When
        List<ResenaResponse> resultado = resenaService.listar(TOKEN);

        // Then
        assertTrue(resultado.isEmpty());
    }

    // --- OBTENER ---

    @Test
    void obtener_idExistente_retornaResena() {
        // Given
        when(repo.findById(1L)).thenReturn(Optional.of(resena));
        when(usuarioClient.obtenerUsuario(1L, TOKEN)).thenReturn(usuario);

        // When
        ResenaResponse response = resenaService.obtener(1L, TOKEN);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Excelente perfume", response.getComentario());
    }

    @Test
    void obtener_idNoExistente_lanzaEntityNotFoundException() {
        // Given
        when(repo.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(EntityNotFoundException.class,
                () -> resenaService.obtener(99L, TOKEN));
    }

    // --- ACTUALIZAR ---

    @Test
    void actualizar_conDatosValidos_retornaResenaActualizada() {
        // Given
        ResenaDTO dto = new ResenaDTO();
        dto.setPuntuacion(4);
        dto.setComentario("Muy bueno");
        dto.setFechaResena("2026-02-01");
        dto.setUsuarioId(1L);

        Resena actualizada = new Resena(1L, 4, "Muy bueno", "2026-02-01", 1L);

        when(usuarioClient.obtenerUsuario(1L, TOKEN)).thenReturn(usuario);
        when(repo.findById(1L)).thenReturn(Optional.of(resena));
        when(repo.save(any(Resena.class))).thenReturn(actualizada);

        // When
        ResenaResponse response = resenaService.actualizar(1L, dto, TOKEN);

        // Then
        assertNotNull(response);
        assertEquals(4, response.getPuntuacion());
        assertEquals("Muy bueno", response.getComentario());
        verify(repo).save(any(Resena.class));
    }

    @Test
    void actualizar_usuarioNoExiste_lanzaExcepcion() {
        // Given
        ResenaDTO dto = new ResenaDTO();
        dto.setPuntuacion(3);
        dto.setComentario("Regular");
        dto.setFechaResena("2026-03-01");
        dto.setUsuarioId(99L);

        when(usuarioClient.obtenerUsuario(99L, TOKEN)).thenReturn(null);

        // When / Then
        assertThrows(RuntimeException.class,
                () -> resenaService.actualizar(1L, dto, TOKEN));
        verify(repo, never()).save(any());
    }

    @Test
    void actualizar_idNoExistente_lanzaEntityNotFoundException() {
        // Given
        ResenaDTO dto = new ResenaDTO();
        dto.setPuntuacion(3);
        dto.setComentario("Regular");
        dto.setFechaResena("2026-03-01");
        dto.setUsuarioId(1L);

        when(usuarioClient.obtenerUsuario(1L, TOKEN)).thenReturn(usuario);
        when(repo.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(EntityNotFoundException.class,
                () -> resenaService.actualizar(99L, dto, TOKEN));
    }

    // --- ELIMINAR ---

    @Test
    void eliminar_idExistente_eliminaCorrectamente() {
        // Given
        doNothing().when(repo).deleteById(1L);

        // When
        resenaService.eliminar(1L);

        // Then
        verify(repo).deleteById(1L);
    }
}
