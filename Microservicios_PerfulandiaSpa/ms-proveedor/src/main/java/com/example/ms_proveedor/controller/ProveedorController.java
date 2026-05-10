package com.example.ms_proveedor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_proveedor.dto.ApiResponse;
import com.example.ms_proveedor.dto.ProveedorDTO;
import com.example.ms_proveedor.model.Proveedor;

import jakarta.validation.Valid;

import com.example.ms_proveedor.service.ProveedorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {
    private final ProveedorService proveedorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Proveedor>> crear(@Valid @RequestBody ProveedorDTO dto) {
        
        Proveedor proveedor = proveedorService.crear(dto);
        return ResponseEntity.status(201).body(
                ApiResponse.<Proveedor>builder()
                        .success(true)
                        .message("Orden Creada")
                        .data(proveedor)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Proveedor>>> listar() {

    return ResponseEntity.ok(
            ApiResponse.<List<Proveedor>>builder()
                    .success(true)
                    .message("Listado obtenido")
                    .data(proveedorService.listar())
                    .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Proveedor>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Proveedor>builder()
                        .success(true)
                        .message("Orden obtenida")
                        .data(proveedorService.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Proveedor>> actualizar(@PathVariable Long id, @Valid @RequestBody ProveedorDTO dto) {

        Proveedor proveedor = proveedorService.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Proveedor>builder()
                        .success(true)
                        .message("Orden actualizada")
                        .data(proveedor)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        proveedorService.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Proveedor eliminado")
                        .build()
        );
    }
}
