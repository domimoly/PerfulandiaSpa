package com.example.ms_reserva.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.ms_reserva.dto.*;
import com.example.ms_reserva.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<ReservaResponse>> crear(
            @Valid @RequestBody ReservaDTO dto,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(201).body(
                ApiResponse.<ReservaResponse>builder()
                        .success(true)
                        .message("Reserva creada")
                        .data(service.crear(dto, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<ReservaResponse>>> listar(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<List<ReservaResponse>>builder()
                        .success(true)
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<ReservaResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<ReservaResponse>builder()
                        .success(true)
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<ReservaResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ReservaDTO dto,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<ReservaResponse>builder()
                        .success(true)
                        .message("Reserva actualizada")
                        .data(service.actualizar(id, dto, token))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Reserva eliminada")
                        .build()
        );
    }
}