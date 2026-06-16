package com.example.ms_cliente.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void debeGuardarCliente() {
        Cliente cliente = new Cliente(null, "2026-01-10", "Av. Principal 456", 1L);
        Cliente guardado = clienteRepository.save(cliente);

        assertNotNull(guardado.getId());
        assertEquals("2026-01-10", guardado.getFechaRegistro());
        assertEquals("Av. Principal 456", guardado.getDireccionEnvio());
        assertEquals(1L, guardado.getUsuarioId());
    }

    @Test
    void debeBuscarClientePorId() {
        Cliente cliente = new Cliente(null, "2026-02-15", "Las Condes 303", 2L);
        Cliente guardado = clienteRepository.save(cliente);

        Optional<Cliente> resultado = clienteRepository.findById(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Las Condes 303", resultado.get().getDireccionEnvio());
        assertEquals(2L, resultado.get().getUsuarioId());
    }

    @Test
    void debeListarClientes() {
        clienteRepository.save(new Cliente(null, "2026-01-01", "Providencia 101", 1L));
        clienteRepository.save(new Cliente(null, "2026-02-01", "Ñuñoa 202", 2L));

        List<Cliente> resultado = clienteRepository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarCliente() {
        Cliente cliente = new Cliente(null, "2026-03-01", "Santiago Centro 505", 3L);
        Cliente guardado = clienteRepository.save(cliente);

        clienteRepository.deleteById(guardado.getId());
        Optional<Cliente> resultado = clienteRepository.findById(guardado.getId());

        assertFalse(resultado.isPresent());
    }

    @Test
    void debeActualizarCliente() {
        Cliente cliente = new Cliente(null, "2026-01-10", "Dirección Original", 1L);
        Cliente guardado = clienteRepository.save(cliente);

        guardado.setDireccionEnvio("Dirección Actualizada");
        clienteRepository.save(guardado);

        Optional<Cliente> resultado = clienteRepository.findById(guardado.getId());
        assertTrue(resultado.isPresent());
        assertEquals("Dirección Actualizada", resultado.get().getDireccionEnvio());
    }

}
