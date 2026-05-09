package com.example.ms_orden.controller;

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

import com.example.ms_orden.dto.ApiResponse;
import com.example.ms_orden.dto.OrdenDTO;
import com.example.ms_orden.model.Orden;
import com.example.ms_orden.service.OrdenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenController {
    private final OrdenService ordenService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Orden>> crear(@Valid @RequestBody OrdenDTO dto) {
        
        Orden orden = ordenService.crear(dto);
        return ResponseEntity.status(201).body(
                ApiResponse.<Orden>builder()
                        .success(true)
                        .message("Orden Creada")
                        .data(orden)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Orden>>> listar() {

    return ResponseEntity.ok(
            ApiResponse.<List<Orden>>builder()
                    .success(true)
                    .message("Listado obtenido")
                    .data(ordenService.listar())
                    .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Orden>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Orden>builder()
                        .success(true)
                        .message("Orden obtenida")
                        .data(ordenService.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Orden>> actualizar(@PathVariable Long id, @Valid @RequestBody OrdenDTO dto) {

        Orden orden = ordenService.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Orden>builder()
                        .success(true)
                        .message("Orden actualizada")
                        .data(orden)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        ordenService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Orden eliminada")
                        .build()
        );
    }
}
