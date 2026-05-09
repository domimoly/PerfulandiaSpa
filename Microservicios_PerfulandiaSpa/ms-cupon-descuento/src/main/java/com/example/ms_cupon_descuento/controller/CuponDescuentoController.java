package com.example.ms_cupon_descuento.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_cupon_descuento.dto.ApiResponse;
import com.example.ms_cupon_descuento.dto.CuponResponseDTO;
import com.example.ms_cupon_descuento.model.CuponDescuento;
import com.example.ms_cupon_descuento.service.CuponDescuentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cupones")
@RequiredArgsConstructor
public class CuponDescuentoController {
    // Pendiente | Agregar los roles admin / user
    // Verificar el GetMapping de /validar/{codigo}
    private final CuponDescuentoService cuponService;

    @PostMapping
    public ResponseEntity<ApiResponse<CuponDescuento>> crear(@Valid @RequestBody CuponDescuento cupon) {
        return ResponseEntity.status(201).body(
                ApiResponse.<CuponDescuento>builder()
                        .success(true)
                        .message("Cupón creado exitosamente")
                        .data(cuponService.crear(cupon))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CuponDescuento>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<CuponDescuento>>builder()
                        .success(true)
                        .message("Lista de cupones obtenida")
                        .data(cuponService.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CuponDescuento>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<CuponDescuento>builder()
                        .success(true)
                        .message("Cupón encontrado")
                        .data(cuponService.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CuponDescuento>> actualizar(@PathVariable Long id, @Valid @RequestBody CuponDescuento cupon) {
        return ResponseEntity.ok(
                ApiResponse.<CuponDescuento>builder()
                        .success(true)
                        .message("Cupón actualizado exitosamente")
                        .data(cuponService.actualizar(id, cupon))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        cuponService.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Cupón eliminado exitosamente")
                        .build()
        );
    }

    // - + - Validación y Aplicar el cupón - + -
    @GetMapping("/validar/{codigo}")
    public ResponseEntity<CuponResponseDTO> validar(@PathVariable String codigo) {
        double porcentaje = cuponService.validarYAplicarCupon(codigo);
        CuponResponseDTO response = new CuponResponseDTO(codigo, porcentaje);
        return ResponseEntity.ok(response);
    }
}
