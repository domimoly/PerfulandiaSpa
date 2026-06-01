package com.example.ms_proveedor.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_proveedor.client.SucursalClient;
import com.example.ms_proveedor.dto.ProveedorDTO;
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

    public Proveedor crear(ProveedorDTO dto, String token) {
        log.info("Crear proveedor", keyValue("Nombre de Proveedor", dto.getNombre()));

        var sucursalR = sucursalClient.obtenerSucursal(dto.getSucursal(), token);
        if (sucursalR == null) {
            throw new RuntimeException("Sucursal no existe");
        }

        Proveedor p = new Proveedor(null, dto.getNombre(), dto.getEmail(), dto.getTelefono(), dto.getDireccion(), dto.getSucursal());
        return proveedorRepo.save(p);
    }

    public List<Proveedor> listar(){
        log.info("Listando todos los proveedores");
        return proveedorRepo.findAll();
    }

    public Proveedor obtener(Long id) {
        log.info("Obteniendo proveedor", keyValue("id", id));

        return proveedorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
    }

    public Proveedor actualizar(Long id, ProveedorDTO dto, String token){
        log.info("Actualizar proveedor", keyValue("id", id));

        var sucursalR = sucursalClient.obtenerSucursal(dto.getSucursal(), token);
        if (sucursalR == null) {
            throw new RuntimeException("Sucursal no existe");
        }

        Proveedor p = obtener(id);
        p.setNombre(dto.getNombre());
        p.setEmail(dto.getEmail());
        p.setTelefono(dto.getTelefono());
        p.setDireccion(dto.getDireccion());
        p.setSucursal(dto.getSucursal());
        
        return proveedorRepo.save(p);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar proveedor", keyValue("id", id));
        proveedorRepo.deleteById(id);
    }
}
