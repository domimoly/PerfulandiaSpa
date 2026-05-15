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
        log.info("Crear registro de inventario", keyValue("Producto ID", dto.getProducto()), keyValue("Sucursal ID", dto.getSucursal()));

        var productoR = productoClient.obtenerProducto(dto.getProducto(), token);
        var sucursalR = sucursalClient.obtenerSucursal(dto.getSucursal(), token);

        if (productoR == null) {
            throw new RuntimeException("Producto no existe");
        }

        if (sucursalR == null) {
            throw new RuntimeException("Sucursal no existe");
        }

        Inventario inventario = invRepo.save(
            new Inventario(null, dto.getProducto(), dto.getSucursal(), dto.getCantidad()));
        
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
        var productoR = productoClient.obtenerProducto(dto.getProducto(), token);
        var sucursalR = sucursalClient.obtenerSucursal(dto.getSucursal(), token);

        if (productoR == null) {
            throw new RuntimeException("Producto no existe");
        }

        if (sucursalR == null) {
            throw new RuntimeException("Sucursal no existe");
        }

        Inventario i = invRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));

        i.setProducto(dto.getProducto());
        i.setSucursal(dto.getSucursal());
        i.setCantidad(dto.getCantidad());
        return mapToResponse(invRepo.save(i), token);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar inventario", keyValue("id", id));
        invRepo.deleteById(id);
    }

    private InventarioResponse mapToResponse(Inventario inventario, String token) {
        var productoR = productoClient.obtenerProducto(inventario.getProducto(), token);
        var sucursalR = sucursalClient.obtenerSucursal(inventario.getSucursal(), token);
        return InventarioResponse.builder()
                .id(inventario.getId())
                .producto(productoR)
                .sucursal(sucursalR)
                .cantidad(inventario.getCantidad())
                .build();
    }
}
