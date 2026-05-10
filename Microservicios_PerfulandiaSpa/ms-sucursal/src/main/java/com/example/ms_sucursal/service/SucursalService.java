package com.example.ms_sucursal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_sucursal.dto.SucursalDTO;
import com.example.ms_sucursal.model.Sucursal;
import com.example.ms_sucursal.repository.SucursalRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public Sucursal crear(SucursalDTO dto) {
        log.info("Crear sucursal", keyValue("nombre", dto.getNombre()));

        Sucursal s = new Sucursal(null, dto.getNombre(), dto.getDireccion(), dto.getHorarioApertura(), dto.getPoliticas());
        return sucursalRepository.save(s);
    }

    public List<Sucursal> listar() {
        log.info("Listar sucursales");
        return sucursalRepository.findAll();
    }

    public Sucursal obtener(Long id) {
        log.info("Obtener sucursal", keyValue("id", id));

        return sucursalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));
    }

    public Sucursal actualizar(Long id, SucursalDTO dto) {
        log.info("Actualizar sucursal", keyValue("id", id));

        Sucursal s = obtener(id);
        s.setNombre(dto.getNombre());
        s.setDireccion(dto.getDireccion());
        s.setHorarioApertura(dto.getHorarioApertura());
        s.setPoliticas(dto.getPoliticas());

        return sucursalRepository.save(s);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar sucursal", keyValue("id", id));
        sucursalRepository.deleteById(id);
    }
}
