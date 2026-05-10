package com.example.ms_inventario.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.repository.InventarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventarioService {
    private final InventarioRepository invRepo;

    public Inventario crear(InventarioDTO dto) {
        log.info("Crear registro de inventario", keyValue("Producto ID", dto.getProductoId()), keyValue("Sucursal ID", dto.getSucursalId()));

        Inventario i = new Inventario(null, dto.getProductoId(), dto.getSucursalId(), dto.getCantidad());
        return invRepo.save(i);
    }

    public List<Inventario> listar() {
        log.info("Listar inventario");
        return invRepo.findAll();
    }

    public Inventario obtener(Long id) {
        log.info("Obtener el inventario", keyValue("id", id));

        return invRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parte del inventario no encontrada"));
    }

    public Inventario actualizar(Long id, InventarioDTO dto) {
        log.info("Actualizar inventario", keyValue("id", id));

        Inventario i = obtener(id);
        i.setProductoId(dto.getProductoId());
        i.setSucursalId(dto.getSucursalId());
        i.setCantidad(dto.getCantidad());
        return invRepo.save(i);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar inventario", keyValue("id", id));
        invRepo.deleteById(id);
    }
}
