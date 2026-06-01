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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_proveedor.dto.ApiResponse;
import com.example.ms_proveedor.dto.ProveedorDTO;
import com.example.ms_proveedor.dto.ProveedorResponse;

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
    public ResponseEntity<ApiResponse<ProveedorResponse>> crear(@Valid @RequestBody ProveedorDTO dto, @RequestHeader("Authorization") String token) {
        
        ProveedorResponse proveedor = proveedorService.crear(dto, token);
        return ResponseEntity.status(201).body(
                ApiResponse.<ProveedorResponse>builder()
                        .success(true)
                        .message("Proveedor Creado")
                        .data(proveedor)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<ProveedorResponse>>> listar(@RequestHeader("Authorization") String token) {

    return ResponseEntity.ok(
            ApiResponse.<List<ProveedorResponse>>builder()
                    .success(true)
                    .message("Listado Obtenido")
                    .data(proveedorService.listar(token))
                    .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<ProveedorResponse>> obtener(@PathVariable Long id, @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<ProveedorResponse>builder()
                        .success(true)
                        .message("Proveedor Obtenido")
                        .data(proveedorService.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProveedorResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody ProveedorDTO dto, @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<ProveedorResponse>builder()
                        .success(true)
                        .message("Proveedor Actualizado")
                        .data(proveedorService.actualizar(id, dto, token))
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
                        .message("Proveedor Eliminado")
                        .build()
        );
    }
}
