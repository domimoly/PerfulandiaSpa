package com.example.ms_producto.controller;
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

import com.example.ms_producto.dto.ApiResponse;
import com.example.ms_producto.dto.ProductoDTO;
import com.example.ms_producto.model.Producto;
import com.example.ms_producto.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Producto>> crear(@Valid @RequestBody ProductoDTO dto) {
        
        Producto producto = productoService.crear(dto);
        return ResponseEntity.status(201).body(
                ApiResponse.<Producto>builder()
                        .success(true)
                        .message("Producto Creado")
                        .data(producto)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Producto>>> listar() {

    return ResponseEntity.ok(
            ApiResponse.<List<Producto>>builder()
                    .success(true)
                    .message("Listado obtenido")
                    .data(productoService.listar())
                    .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Producto>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Producto>builder()
                        .success(true)
                        .message("Producto Obtenido")
                        .data(productoService.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Producto>> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {

        Producto producto = productoService.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Producto>builder()
                        .success(true)
                        .message("Producto actualizado")
                        .data(producto)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        productoService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Producto eliminado")
                        .build()
        );
    }
}
