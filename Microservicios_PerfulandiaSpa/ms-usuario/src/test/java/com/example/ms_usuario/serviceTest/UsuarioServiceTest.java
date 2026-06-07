package com.example.ms_usuario.serviceTest;

import com.example.ms_usuario.dto.UsuarioDTO;
import com.example.ms_usuario.model.Usuario;
import com.example.ms_usuario.repository.UsuarioRepository;
import com.example.ms_usuario.service.UsuarioService;

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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private UsuarioService service;

    @Test
    void deberiaRetornarUsuarioCuandoExiste() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Perez");
        usuario.setUsername("juanp");
        usuario.setEmail("juan@gmail.com");
        usuario.setDireccion("Santiago Centro 101");
        usuario.setTelefono("911111111");
        usuario.setTipo("USER");
        usuario.setPassword("1234");

        when(repo.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = service.obtener(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());

        verify(repo).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoExiste() {

        when(repo.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> service.obtener(99L)
        );

        assertEquals("Usuario no encontrado", ex.getMessage());

        verify(repo).findById(99L);
    }

    @Test
    void deberiaRetornarListaUsuarios() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Cliente Uno");
        usuario.setUsername("cliente1");
        usuario.setEmail("cliente1@gmail.com");
        usuario.setDireccion("Av. Principal 456");
        usuario.setTelefono("987654321");
        usuario.setTipo("USER");
        usuario.setPassword("1234");

        when(repo.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = service.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Cliente Uno", resultado.get(0).getNombre());

        verify(repo).findAll();
    }

    @Test
    void deberiaCrearUsuarioCorrectamente() {

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Carlos Rojas");
        dto.setUsername("carlosr");
        dto.setEmail("carlos@gmail.com");
        dto.setDireccion("Las Condes 303");
        dto.setTelefono("933333333");
        dto.setTipo("ADMIN");
        dto.setPassword("1234");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setNombre(dto.getNombre());
        usuarioGuardado.setUsername(dto.getUsername());
        usuarioGuardado.setEmail(dto.getEmail());
        usuarioGuardado.setDireccion(dto.getDireccion());
        usuarioGuardado.setTelefono(dto.getTelefono());
        usuarioGuardado.setTipo(dto.getTipo());
        usuarioGuardado.setPassword(dto.getPassword());

        when(repo.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        Usuario resultado = service.crear(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Carlos Rojas", resultado.getNombre());

        verify(repo).save(any(Usuario.class));
    }

    @Test
    void deberiaActualizarUsuarioCorrectamente() {

        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setNombre("Usuario Antiguo");
        existente.setUsername("antiguo");
        existente.setEmail("antiguo@gmail.com");
        existente.setDireccion("Direccion Antigua");
        existente.setTelefono("111111111");
        existente.setTipo("USER");
        existente.setPassword("1234");

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Admin Perfulandia");
        dto.setUsername("admin");
        dto.setEmail("admin@perfulandia.cl");
        dto.setDireccion("Meiggs 123");
        dto.setTelefono("912345678");
        dto.setTipo("ADMIN");
        dto.setPassword("1234");

        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = service.actualizar(1L, dto);

        assertEquals(1L, resultado.getId());
        assertEquals("Admin Perfulandia", resultado.getNombre());
        assertEquals("admin", resultado.getUsername());
        assertEquals("ADMIN", resultado.getTipo());

        verify(repo).findById(1L);
        verify(repo).save(existente);
    }

    @Test
    void deberiaEliminarUsuarioPorId() {

        doNothing().when(repo).deleteById(1L);

        service.eliminar(1L);

        verify(repo).deleteById(1L);
    }
}