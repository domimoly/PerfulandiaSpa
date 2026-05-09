package com.example.ms_orden.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_orden.dto.OrdenDTO;
import com.example.ms_orden.model.Orden;
import com.example.ms_orden.repository.OrdenRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdenService {
    private final OrdenRepository repo;

    public Orden crear(OrdenDTO dto) {
        log.info("Crear orden", keyValue("Numero de Orden", dto.getNumeroOrden()));

        Orden o = new Orden(null, dto.getNumeroOrden(), dto.getFechaCreacion(), dto.getFechaRecibida(), dto.getTotal(), dto.getDescuentoAplicado());
        return repo.save(o);
    }

    public List<Orden> listar() {
        log.info("Listar ordenes");
        return repo.findAll();
    }

    public Orden obtener(Long id) {
        log.info("Obtener orden", keyValue("id", id));

        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada"));
    }

    public Orden actualizar(Long id, OrdenDTO dto) {
        log.info("Actualizar orden", keyValue("id", id));

        Orden o = obtener(id);
        o.setNumeroOrden(dto.getNumeroOrden());
        o.setFechaCreacion(dto.getFechaCreacion());
        o.setFechaRecibida(dto.getFechaRecibida());
        o.setTotal(dto.getTotal());
        o.setDescuentoAplicado(dto.getDescuentoAplicado());

        return repo.save(o);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar orden", keyValue("id", id));
        repo.deleteById(id);
    }
}
