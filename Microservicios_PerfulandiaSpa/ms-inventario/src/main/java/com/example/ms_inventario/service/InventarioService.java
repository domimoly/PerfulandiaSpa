package com.example.ms_inventario.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_inventario.client.ProductoClient;
import com.example.ms_inventario.client.SucursalClient;
import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.dto.InventarioResponse;
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
    private final ProductoClient productoClient;
    private final SucursalClient sucursalClient;

    public InventarioResponse crear(InventarioDTO dto, String token) {
        log.info("Crear registro de inventario", keyValue("Producto ID", dto.getProductoId()), keyValue("Sucursal ID", dto.getSucursalId()));

        var producto = productoClient.obtenerProducto(dto.getProductoId(), token);
        var sucursal = sucursalClient.obtenerSucursal(dto.getSucursalId(), token);

        if (producto == null) {
            throw new RuntimeException("Producto no existe");
        }
        if (sucursal == null) {
            throw new RuntimeException("Sucursal no existe");
        }

        Inventario inventario = invRepo.save(
            new Inventario(null, dto.getProductoId(), dto.getSucursalId(), dto.getCantidad()));
        
        return mapToResponse(inventario, token);
    }

    public List<InventarioResponse> listar(String token) {
        log.info("Listar inventario");
        return invRepo.findAll()
                .stream()
                .map(t -> mapToResponse(t, token))
                .toList();
    }

    public InventarioResponse obtener(Long id, String token) {
        log.info("Obtener el inventario", keyValue("id", id));

        Inventario inventario = invRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));
                
        return mapToResponse(inventario, token);
    }

    public InventarioResponse actualizar(Long id, InventarioDTO dto, String token) {
        log.info("Actualizar inventario", keyValue("id", id));
        var producto = productoClient.obtenerProducto(dto.getProductoId(), token);

        if (producto == null) {
            throw new RuntimeException("Producto no existe");
        }

        Inventario i = invRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));

        i.setProductoId(dto.getProductoId());
        i.setSucursalId(dto.getSucursalId());
        i.setCantidad(dto.getCantidad());
        return mapToResponse(invRepo.save(i), token);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar inventario", keyValue("id", id));
        invRepo.deleteById(id);
    }

    private InventarioResponse mapToResponse(Inventario inventario, String token) {
        var producto = productoClient.obtenerProducto(inventario.getProductoId(), token);
        var sucursal = sucursalClient.obtenerSucursal(inventario.getSucursalId(), token);
        return InventarioResponse.builder()
                .id(inventario.getId())
                .productoId(producto)
                .sucursalId(sucursal)
                .cantidad(inventario.getCantidad())
                .build();
    }
}
