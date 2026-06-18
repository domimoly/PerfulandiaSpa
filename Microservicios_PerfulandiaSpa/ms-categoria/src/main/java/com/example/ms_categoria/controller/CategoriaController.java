package com.example.ms_categoria.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

@Tag(name = "Categorias", description = "Gestión de categorías de PerfulandiaSpa")
@RestController
@RequestMapping("/api/v2/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría de productos")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Categoria>> crear(@Valid @RequestBody CategoriaDTO dto) {

        Categoria categoria = categoriaService.crear(dto);
        return ResponseEntity.status(201).body(
                ApiResponse.<Categoria>builder()
                        .success(true)
                        .message("Categoría creada")
                        .data(categoria)
                        .build()
        );
    }

    @Operation(summary = "Listar categorías", description = "Retorna todas las categorías disponibles")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado")
    })
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

    @Operation(summary = "Obtener categoría", description = "Retorna una categoría por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Categoría obtenida"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<EntityModel<Categoria>>> obtener(
            @Parameter(description = "ID de la categoría a obtener", example = "1")
            @PathVariable Long id) {

        Categoria categoria = categoriaService.obtener(id);
        EntityModel<Categoria> recurso = EntityModel.of(categoria);

        recurso.add(linkTo(methodOn(CategoriaController.class).obtener(id)).withSelfRel());
        recurso.add(linkTo(methodOn(CategoriaController.class).listar()).withRel("all"));
        recurso.add(linkTo(methodOn(CategoriaController.class).actualizar(id, null)).withRel("update"));
        recurso.add(linkTo(methodOn(CategoriaController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<Categoria>>builder()
                        .success(true)
                        .message("Categoría obtenida")
                        .data(recurso)
                        .build()
        );
    }

    @Operation(summary = "Actualizar categoría", description = "Actualiza los datos de una categoría existente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Categoría actualizada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Categoria>> actualizar(
            @Parameter(description = "ID de la categoría a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CategoriaDTO dto) {

        Categoria categoria = categoriaService.actualizar(id, dto);
        return ResponseEntity.ok(
                ApiResponse.<Categoria>builder()
                        .success(true)
                        .message("Categoría actualizada")
                        .data(categoria)
                        .build()
        );
    }

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría por ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Categoría eliminada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @Parameter(description = "ID de la categoría a eliminar", example = "1")
            @PathVariable Long id) {

        categoriaService.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Categoría eliminada")
                        .build()
        );
    }
}