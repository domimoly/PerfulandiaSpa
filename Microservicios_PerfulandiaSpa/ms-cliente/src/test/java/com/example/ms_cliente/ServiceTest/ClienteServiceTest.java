package com.example.ms_cliente.ServiceTest;

import com.example.ms_cliente.client.UsuarioClient;
import com.example.ms_cliente.dto.ClienteDTO;
import com.example.ms_cliente.dto.ClienteResponse;
import com.example.ms_cliente.dto.UsuarioResponse;
import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.repository.ClienteRepository;
import com.example.ms_cliente.service.ClienteService;

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
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepo;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private ClienteService service;

    private final String token = "Bearer test";

    @Test
    void deberiaObtenerClienteCuandoExiste() {

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setFechaRegistro("2026-01-10");
        cliente.setDireccionEnvio("Av. Principal 456");
        cliente.setUsuarioId(1L);

        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setId(1L);
        usuario.setNombre("Cliente Uno");
        usuario.setUsername("cliente1");
        usuario.setEmail("cliente1@gmail.com");
        usuario.setDireccion("Av. Principal 456");
        usuario.setTelefono("987654321");
        usuario.setTipo("USER");

        when(clienteRepo.findById(1L))
                .thenReturn(Optional.of(cliente));

        when(usuarioClient.obtenerUsuario(1L, token))
                .thenReturn(usuario);

        ClienteResponse resultado = service.obtener(1L, token);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(clienteRepo).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoClienteNoExiste() {

        when(clienteRepo.findById(99L))
                .thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> service.obtener(99L, token)
        );

        assertEquals("Cliente no encontrado", ex.getMessage());

        verify(clienteRepo).findById(99L);
    }

    @Test
    void deberiaRetornarListaClientes() {

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setFechaRegistro("2026-01-10");
        cliente.setDireccionEnvio("Av. Principal 456");
        cliente.setUsuarioId(1L);

        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setId(1L);
        usuario.setNombre("Cliente Uno");
        usuario.setUsername("cliente1");
        usuario.setEmail("cliente1@gmail.com");
        usuario.setDireccion("Av. Principal 456");
        usuario.setTelefono("987654321");
        usuario.setTipo("USER");

        when(clienteRepo.findAll())
                .thenReturn(List.of(cliente));

        when(usuarioClient.obtenerUsuario(1L, token))
                .thenReturn(usuario);

        List<ClienteResponse> resultado = service.listar(token);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());

        verify(clienteRepo).findAll();
    }

    @Test
    void deberiaCrearClienteCorrectamente() {

        ClienteDTO dto = new ClienteDTO();
        dto.setFechaRegistro("2026-01-10");
        dto.setDireccionEnvio("Av. Principal 456");
        dto.setUsuarioId(1L);

        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setId(1L);
        usuario.setNombre("Cliente Uno");
        usuario.setUsername("cliente1");
        usuario.setEmail("cliente1@gmail.com");
        usuario.setDireccion("Av. Principal 456");
        usuario.setTelefono("987654321");
        usuario.setTipo("USER");

        Cliente clienteGuardado = new Cliente();
        clienteGuardado.setId(1L);
        clienteGuardado.setFechaRegistro("2026-01-10");
        clienteGuardado.setDireccionEnvio("Av. Principal 456");
        clienteGuardado.setUsuarioId(1L);

        when(usuarioClient.obtenerUsuario(1L, token))
                .thenReturn(usuario);

        when(clienteRepo.save(any(Cliente.class)))
                .thenReturn(clienteGuardado);

        ClienteResponse resultado = service.crear(dto, token);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(clienteRepo).save(any(Cliente.class));
    }

    @Test
    void deberiaActualizarClienteCorrectamente() {

        Cliente existente = new Cliente();
        existente.setId(1L);
        existente.setFechaRegistro("2026-01-10");
        existente.setDireccionEnvio("Direccion Antigua");
        existente.setUsuarioId(1L);

        ClienteDTO dto = new ClienteDTO();
        dto.setFechaRegistro("2026-02-15");
        dto.setDireccionEnvio("Las Condes 303");
        dto.setUsuarioId(2L);

        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setId(2L);
        usuario.setNombre("Juan Perez");
        usuario.setUsername("juanp");
        usuario.setEmail("juan@gmail.com");
        usuario.setDireccion("Las Condes 303");
        usuario.setTelefono("911111111");
        usuario.setTipo("USER");

        when(usuarioClient.obtenerUsuario(2L, token))
                .thenReturn(usuario);

        when(clienteRepo.findById(1L))
                .thenReturn(Optional.of(existente));

        when(clienteRepo.save(any(Cliente.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ClienteResponse resultado = service.actualizar(1L, dto, token);

        assertNotNull(resultado);
        assertEquals("Las Condes 303", resultado.getDireccionEnvio());

        verify(clienteRepo).findById(1L);
        verify(clienteRepo).save(existente);
    }

    @Test
    void deberiaEliminarClientePorId() {

        doNothing().when(clienteRepo).deleteById(1L);

        service.eliminar(1L);

        verify(clienteRepo).deleteById(1L);
    }
}