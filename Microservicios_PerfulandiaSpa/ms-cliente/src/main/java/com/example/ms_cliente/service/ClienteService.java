package com.example.ms_cliente.service;


import org.springframework.stereotype.Service;
import com.example.ms_cliente.client.UsuarioClient;
import com.example.ms_cliente.dto.ClienteDTO;
import com.example.ms_cliente.dto.ClienteResponse;
import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.repository.ClienteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepo;
    private final UsuarioClient usuarioClient;

    public ClienteResponse crear(ClienteDTO dto, String token){
        log.info("Crear cliente", keyValue("usuarioId", dto.getUsuarioId()));

        var usuario = usuarioClient.obtenerUsuario(dto.getUsuarioId(), token);

        if (usuario == null){
            throw new RuntimeException("Usuario no existe");
        }

        Cliente cliente = clienteRepo.save(
                new Cliente(null,dto.getFechaRegistro(), dto.getDireccionEnvio(), dto.getUsuarioId())
        );

        return mapToResponse(cliente, token);

    }

    public List<ClienteResponse> listar(String token) {
        return clienteRepo.findAll()
                .stream()
                .map(c -> mapToResponse(c, token))
                .toList();
    }

    public ClienteResponse obtener(Long id, String token) {

        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
                
        return mapToResponse(cliente, token);
    }

    public ClienteResponse actualizar(Long id, ClienteDTO dto, String token){

        var usuario = usuarioClient.obtenerUsuario(dto.getUsuarioId(), token);

        if (usuario == null){
            throw new RuntimeException("Usuario no existe");
        }

        Cliente c = clienteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

                c.setFechaRegistro(dto.getFechaRegistro());
                c.setDireccionEnvio(dto.getDireccionEnvio());
                c.setUsuarioId(dto.getUsuarioId());

                return mapToResponse(clienteRepo.save(c), token);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar cliente", keyValue("id", id));
        clienteRepo.deleteById(id);
    }

    private ClienteResponse mapToResponse(Cliente cliente, String token) {
        var usuario = usuarioClient.obtenerUsuario(cliente.getUsuarioId(), token);
        return ClienteResponse.builder()
                .id(cliente.getId())
                .fechaRegistro(cliente.getFechaRegistro())
                .direccionEnvio(cliente.getDireccionEnvio())
                .usuario(usuario)
                .build();
    }
    
}
