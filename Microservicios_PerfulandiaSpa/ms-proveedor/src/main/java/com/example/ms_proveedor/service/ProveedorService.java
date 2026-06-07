package com.example.ms_proveedor.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_proveedor.client.ProductoClient;
import com.example.ms_proveedor.client.SucursalClient;
import com.example.ms_proveedor.dto.ProveedorDTO;
import com.example.ms_proveedor.dto.ProveedorResponse;
import com.example.ms_proveedor.model.Proveedor;
import com.example.ms_proveedor.repository.ProveedorRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProveedorService {
    private final ProveedorRepository proveedorRepo;
    private final SucursalClient sucursalClient;
    private final ProductoClient productoClient;

    public ProveedorResponse crear(ProveedorDTO dto, String token) {
        log.info("Crear proveedor", keyValue("Nombre de Proveedor", dto.getNombre()));

        var sucursalR = sucursalClient.obtenerSucursal(dto.getSucursal(), token);
        if (sucursalR == null) {
            throw new RuntimeException("Sucursal no existe");
        }

        var productoR = productoClient.obtenerProducto(dto.getProducto(), token);
        if (productoR == null) {
            throw new RuntimeException("Producto no existe");
        }
        Proveedor p = proveedorRepo.save(new Proveedor(null, dto.getNombre(), dto.getEmail(), dto.getTelefono(), dto.getDireccion(), dto.getSucursal(), dto.getProducto()));
        return mapToResponse(p, token);
    }

    public List<ProveedorResponse> listar(String token) {
        log.info("Listando todos los proveedores");
        return proveedorRepo.findAll()
            .stream()
            .map(p -> mapToResponse(p, token))
            .toList();
}

    public ProveedorResponse obtener(Long id, String token) {
        log.info("Obteniendo proveedor", keyValue("id", id));

        Proveedor p = proveedorRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
        return mapToResponse(p, token);
    }

    public ProveedorResponse actualizar(Long id, ProveedorDTO dto, String token){
        log.info("Actualizar proveedor", keyValue("id", id));

        var sucursalR = sucursalClient.obtenerSucursal(dto.getSucursal(), token);
        if (sucursalR == null) {
            throw new RuntimeException("Sucursal no existe");
        }

        var productoR = productoClient.obtenerProducto(dto.getProducto(), token);
        if (productoR == null) {
            throw new RuntimeException("Producto no existe");
        }

        Proveedor p = proveedorRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
        p.setNombre(dto.getNombre());
        p.setEmail(dto.getEmail());
        p.setTelefono(dto.getTelefono());
        p.setDireccion(dto.getDireccion());
        p.setSucursal(dto.getSucursal());
        p.setProducto(dto.getProducto());
        
        return mapToResponse(proveedorRepo.save(p), token);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar proveedor", keyValue("id", id));
        proveedorRepo.deleteById(id);
    }

    private ProveedorResponse mapToResponse(Proveedor p, String token) {
    var sucursalR = sucursalClient.obtenerSucursal(p.getSucursal(), token);
    var productoR = productoClient.obtenerProducto(p.getProducto(), token);
    return ProveedorResponse.builder()
        .id(p.getId())
        .nombre(p.getNombre())
        .email(p.getEmail())
        .telefono(p.getTelefono())
        .direccion(p.getDireccion())
        .sucursal(sucursalR)
        .producto(productoR)
        .build();
    }
}
