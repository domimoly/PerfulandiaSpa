package com.example.ms_resena.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_resena.client.UsuarioClient;
import com.example.ms_resena.dto.ResenaDTO;
import com.example.ms_resena.dto.ResenaResponse;
import com.example.ms_resena.model.Resena;
import com.example.ms_resena.repository.ResenaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResenaService {

    private final ResenaRepository repo;
    private final UsuarioClient usuarioClient;

    public ResenaResponse crear(ResenaDTO dto, String token) {

        log.info("Crear resena",
                keyValue("usuarioId", dto.getUsuarioId()));

        var usuario = usuarioClient.obtenerUsuario(dto.getUsuarioId(), token);

        if (usuario == null) {
            throw new RuntimeException("Usuario no existe");
        }

        Resena resena = repo.save(
                new Resena(
                        null,
                        dto.getPuntuacion(),
                        dto.getComentario(),
                        dto.getFechaResena(),
                        dto.getUsuarioId()));

        return mapToResponse(resena, token);
    }

    public List<ResenaResponse> listar(String token) {

        log.info("Listar resenas");

        return repo.findAll()
                .stream()
                .map(r -> mapToResponse(r, token))
                .toList();
    }

    public ResenaResponse obtener(Long id, String token) {

        log.info("Obtener resena",
                keyValue("id", id));

        Resena resena = repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Resena no encontrada"));

        return mapToResponse(resena, token);
    }

    public ResenaResponse actualizar(Long id, ResenaDTO dto, String token) {

        log.info("Actualizar resena",
                keyValue("id", id));

        var usuario = usuarioClient.obtenerUsuario(dto.getUsuarioId(), token);

        if (usuario == null) {
            throw new RuntimeException("Usuario no existe");
        }

        Resena r = repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Resena no encontrada"));

        r.setPuntuacion(dto.getPuntuacion());
        r.setComentario(dto.getComentario());
        r.setFechaResena(dto.getFechaResena());
        r.setUsuarioId(dto.getUsuarioId());

        return mapToResponse(repo.save(r), token);
    }

    public void eliminar(Long id) {

        log.warn("Eliminar resena",
                keyValue("id", id));

        repo.deleteById(id);
    }

    private ResenaResponse mapToResponse(Resena resena, String token) {

        var usuario = usuarioClient.obtenerUsuario(
                resena.getUsuarioId(), token);

        return ResenaResponse.builder()
                .id(resena.getId())
                .puntuacion(resena.getPuntuacion())
                .comentario(resena.getComentario())
                .fechaResena(resena.getFechaResena())
                .usuario(usuario)
                .build();
    }
}
