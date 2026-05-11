package com.example.ms_devolucion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_devolucion.dto.DevolucionDTO;
import com.example.ms_devolucion.model.Devolucion;
import com.example.ms_devolucion.repository.DevolucionRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class DevolucionService {

    private final DevolucionRepository devolucionRepository;

    public Devolucion crear(DevolucionDTO dto) {
        log.info("Crear devolución", keyValue("fecha", dto.getFechaDevolucion()));

        Devolucion d = new Devolucion(null, dto.getFechaDevolucion(), dto.getMotivo(), dto.getEstado());
        return devolucionRepository.save(d);
    }

    public List<Devolucion> listar() {
        log.info("Listar devoluciones");
        return devolucionRepository.findAll();
    }

    public Devolucion obtener(Long id) {
        log.info("Obtener devolución", keyValue("id", id));

        return devolucionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devolución no encontrada"));
    }

    public Devolucion actualizar(Long id, DevolucionDTO dto) {
        log.info("Actualizar devolución", keyValue("id", id));

        Devolucion d = obtener(id);
        d.setFechaDevolucion(dto.getFechaDevolucion());
        d.setMotivo(dto.getMotivo());
        d.setEstado(dto.getEstado());

        return devolucionRepository.save(d);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar devolución", keyValue("id", id));
        devolucionRepository.deleteById(id);
    }
}
