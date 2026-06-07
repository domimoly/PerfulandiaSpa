package com.example.ms_categoria.controller;

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

import com.example.ms_categoria.dto.ApiResponse;
import com.example.ms_categoria.dto.CategoriaDTO;
import com.example.ms_categoria.model.Categoria;
import com.example.ms_categoria.service.CategoriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Categoria>> crear(@Valid @RequestBody CategoriaDTO dto) {
        
        Categoria categoria = categoriaService.crear(dto);
        return ResponseEntity.status(201).body(
                ApiResponse.<Categoria>builder()
                        .success(true)
                        .message("Categoria Creada")
                        .data(categoria)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Categoria>>> listar() {

    return ResponseEntity.ok(
            ApiResponse.<List<Categoria>>builder()
                    .success(true)
                    .message("Listado obtenido")
                    .data(categoriaService.listar())
                    .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Categoria>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Categoria>builder()
                        .success(true)
                        .message("Categoria obtenida")
                        .data(categoriaService.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Categoria>> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {

        Categoria categoria = categoriaService.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Categoria>builder()
                        .success(true)
                        .message("Categoria actualizada")
                        .data(categoria)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        categoriaService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Categoria eliminada")
                        .build()
        );
    }
}
