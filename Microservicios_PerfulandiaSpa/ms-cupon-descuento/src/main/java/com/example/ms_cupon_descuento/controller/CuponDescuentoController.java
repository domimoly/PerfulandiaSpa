package com.example.ms_cupon_descuento.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_cupon_descuento.dto.ApiResponse;
import com.example.ms_cupon_descuento.dto.CuponDescuentoDTO;
import com.example.ms_cupon_descuento.model.CuponDescuento;
import com.example.ms_cupon_descuento.service.CuponDescuentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cupones")
@RequiredArgsConstructor
public class CuponDescuentoController {
    // Verificar el GetMapping de /validar/{codigo}
    private final CuponDescuentoService cuponService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CuponDescuento>> crear(@Valid @RequestBody CuponDescuentoDTO dto) {
        
        CuponDescuento cupon = cuponService.crear(dto);
        return ResponseEntity.status(201).body(
                ApiResponse.<CuponDescuento>builder()
                        .success(true)
                        .message("Cupon Creada")
                        .data(cupon)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<CuponDescuento>>> listar() {

    return ResponseEntity.ok(
            ApiResponse.<List<CuponDescuento>>builder()
                    .success(true)
                    .message("Listado obtenido")
                    .data(cuponService.listar())
                    .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<CuponDescuento>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<CuponDescuento>builder()
                        .success(true)
                        .message("Cupon obtenido")
                        .data(cuponService.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CuponDescuento>> actualizar(@PathVariable Long id, @Valid @RequestBody CuponDescuentoDTO dto) {

        CuponDescuento cupon = cuponService.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<CuponDescuento>builder()
                        .success(true)
                        .message("Cupon actualizado")
                        .data(cupon)
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
