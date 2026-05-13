package com.example.ms_inventario.controller;

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

import com.example.ms_inventario.dto.ApiResponse;
import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.dto.InventarioResponse;
import com.example.ms_inventario.service.InventarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {
    private final InventarioService invService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<InventarioResponse>> crear(@Valid @RequestBody InventarioDTO dto, @RequestHeader("Authorization") String token) {
        
        return ResponseEntity.status(201).body(
                ApiResponse.<InventarioResponse>builder()
                        .success(true)
                        .message("Inventario Creado")
                        .data(invService.crear(dto, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<InventarioResponse>>> listar(@RequestHeader("Authorization") String token) {
    return ResponseEntity.ok(
            ApiResponse.<List<InventarioResponse>>builder()
                    .success(true)
                    .message("Listado obtenido")
                    .data(invService.listar(token))
                    .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<InventarioResponse>> obtener(@PathVariable Long id, @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<InventarioResponse>builder()
                        .success(true)
                        .message("Orden obtenida")
                        .data(invService.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<InventarioResponse>> actualizar(@PathVariable Long id,@Valid @RequestBody InventarioDTO dto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<InventarioResponse>builder()
                        .success(true)
                        .message("Inventario actualizado")
                        .data(invService.actualizar(id, dto, token))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        invService.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Inventario eliminado")
                        .build()
        );
    }
}
