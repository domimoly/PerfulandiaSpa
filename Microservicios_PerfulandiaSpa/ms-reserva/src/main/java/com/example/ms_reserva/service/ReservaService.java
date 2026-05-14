package com.example.ms_reserva.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_reserva.client.UsuarioClient;
import com.example.ms_reserva.dto.ReservaDTO;
import com.example.ms_reserva.dto.ReservaResponse;
import com.example.ms_reserva.model.Reserva;
import com.example.ms_reserva.repository.ReservaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservaService {

    private final ReservaRepository repo;
    private final UsuarioClient usuarioClient;

    public ReservaResponse crear(ReservaDTO dto, String token) {

        log.info("Crear reserva",
                keyValue("usuarioId", dto.getUsuarioId()));

        var usuario = usuarioClient.obtenerUsuario(dto.getUsuarioId(), token);

        if (usuario == null) {
            throw new RuntimeException("Usuario no existe");
        }

        Reserva reserva = repo.save(
                new Reserva(
                        null,
                        dto.getPuntuacion(),
                        dto.getComentario(),
                        dto.getFechaResena(),
                        dto.getUsuarioId()));

        return mapToResponse(reserva, token);
    }

    public List<ReservaResponse> listar(String token) {

        log.info("Listar reservas");

        return repo.findAll()
                .stream()
                .map(r -> mapToResponse(r, token))
                .toList();
    }

    public ReservaResponse obtener(Long id, String token) {

        log.info("Obtener reserva",
                keyValue("id", id));

        Reserva reserva = repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Reserva no encontrada"));

        return mapToResponse(reserva, token);
    }

    public ReservaResponse actualizar(Long id, ReservaDTO dto, String token) {

        log.info("Actualizar reserva",
                keyValue("id", id));

        var usuario = usuarioClient.obtenerUsuario(dto.getUsuarioId(), token);

        if (usuario == null) {
            throw new RuntimeException("Usuario no existe");
        }

        Reserva r = repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Reserva no encontrada"));

        r.setPuntuacion(dto.getPuntuacion());
        r.setComentario(dto.getComentario());
        r.setFechaResena(dto.getFechaResena());
        r.setUsuarioId(dto.getUsuarioId());

        return mapToResponse(repo.save(r), token);
    }

    public void eliminar(Long id) {

        log.warn("Eliminar reserva",
                keyValue("id", id));

        repo.deleteById(id);
    }

    private ReservaResponse mapToResponse(Reserva reserva, String token) {

        var usuario = usuarioClient.obtenerUsuario(
                reserva.getUsuarioId(), token);

        return ReservaResponse.builder()
                .id(reserva.getId())
                .puntuacion(reserva.getPuntuacion())
                .comentario(reserva.getComentario())
                .fechaResena(reserva.getFechaResena())
                .usuario(usuario)
                .build();
    }
}