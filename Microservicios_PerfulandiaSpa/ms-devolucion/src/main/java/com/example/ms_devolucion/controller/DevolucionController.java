package com.example.ms_devolucion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_devolucion.dto.ApiResponse;
import com.example.ms_devolucion.dto.DevolucionDTO;
import com.example.ms_devolucion.model.Devolucion;
import com.example.ms_devolucion.service.DevolucionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/devoluciones")
@RequiredArgsConstructor
public class DevolucionController {

    private final DevolucionService devolucionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Devolucion>> crear(@Valid @RequestBody DevolucionDTO dto) {

        Devolucion devolucion = devolucionService.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Devolucion>builder()
                        .success(true)
                        .message("Devolución generada")
                        .data(devolucion)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Devolucion>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Devolucion>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(devolucionService.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Devolucion>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Devolucion>builder()
                        .success(true)
                        .message("Devolución obtenida")
                        .data(devolucionService.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Devolucion>> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody DevolucionDTO dto) {

        Devolucion devolucion = devolucionService.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Devolucion>builder()
                        .success(true)
                        .message("Devolución actualizada")
                        .data(devolucion)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        devolucionService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Devolución eliminada")
                        .build()
        );
    }
}
