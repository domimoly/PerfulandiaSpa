package com.example.ms_cupon_descuento.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_cupon_descuento.dto.ApiResponse;
import com.example.ms_cupon_descuento.dto.CuponDescuentoDTO;
import com.example.ms_cupon_descuento.dto.CuponResponse;
import com.example.ms_cupon_descuento.service.CuponDescuentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cupones")
@RequiredArgsConstructor
public class CuponDescuentoController {
    private final CuponDescuentoService cuponService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CuponResponse>> crear(@Valid @RequestBody CuponDescuentoDTO dto, @RequestHeader("Authorization") String token) {
        
        return ResponseEntity.status(201).body(
                ApiResponse.<CuponResponse>builder()
                        .success(true)
                        .message("Cupon Creada")
                        .data(cuponService.crear(dto, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<CuponResponse>>> listar(@RequestHeader("Authorization") String token) {

    return ResponseEntity.ok(
            ApiResponse.<List<CuponResponse>>builder()
                    .success(true)
                    .message("Listado obtenido")
                    .data(cuponService.listar(token))
                    .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<CuponResponse>> obtener(@PathVariable Long id, @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<CuponResponse>builder()
                        .success(true)
                        .message("Cupon obtenido")
                        .data(cuponService.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CuponResponse>> actualizar(@PathVariable Long id,@Valid @RequestBody CuponDescuentoDTO dto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<CuponResponse>builder()
                        .success(true)
                        .message("Cupon actualizado")
                        .data(cuponService.actualizar(id, dto, token))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        cuponService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Cupon eliminado")
                        .build()
        );
    }
}
