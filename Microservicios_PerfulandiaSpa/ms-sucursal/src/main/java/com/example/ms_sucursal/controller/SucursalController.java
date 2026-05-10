package com.example.ms_sucursal.controller;

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

import com.example.ms_sucursal.dto.ApiResponse;
import com.example.ms_sucursal.dto.SucursalDTO;
import com.example.ms_sucursal.model.Sucursal;
import com.example.ms_sucursal.service.SucursalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalService sucursalService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Sucursal>> crear(@Valid @RequestBody SucursalDTO dto) {

        Sucursal sucursal = sucursalService.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Sucursal>builder()
                        .success(true)
                        .message("Sucursal creada")
                        .data(sucursal)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Sucursal>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Sucursal>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(sucursalService.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Sucursal>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Sucursal>builder()
                        .success(true)
                        .message("Sucursal obtenida")
                        .data(sucursalService.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Sucursal>> actualizar(@PathVariable Long id, @Valid @RequestBody SucursalDTO dto) {

        Sucursal sucursal = sucursalService.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Sucursal>builder()
                        .success(true)
                        .message("Sucursal actualizada")
                        .data(sucursal)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        sucursalService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Sucursal eliminada")
                        .build()
        );
    }
}
