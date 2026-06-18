package com.example.ms_proveedor.controller;

import java.util.List;

// Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// HATEOAS
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_proveedor.dto.ApiResponse;
import com.example.ms_proveedor.dto.ProveedorDTO;
import com.example.ms_proveedor.dto.ProveedorResponse;
import com.example.ms_proveedor.service.ProveedorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Proveedores", description = "Gestión de proveedores de PerfulandiaSpa")
@RestController
@RequestMapping("/api/v2/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @Operation(summary = "Crear proveedor", description = "Crea un nuevo proveedor asociado a una sucursal y producto")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProveedorResponse>> crear(
            @Valid @RequestBody ProveedorDTO dto,
            @RequestHeader("Authorization") String token) {

        ProveedorResponse proveedor = proveedorService.crear(dto, token);
        return ResponseEntity.status(201).body(
                ApiResponse.<ProveedorResponse>builder()
                        .success(true)
                        .message("Proveedor Creado")
                        .data(proveedor)
                        .build()
        );
    }

    @Operation(summary = "Listar proveedores", description = "Retorna todos los proveedores disponibles")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<ProveedorResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<ProveedorResponse>>builder()
                        .success(true)
                        .message("Listado Obtenido")
                        .data(proveedorService.listar(token))
                        .build()
        );
    }

    @Operation(summary = "Obtener proveedor", description = "Retorna un proveedor por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Proveedor obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<EntityModel<ProveedorResponse>>> obtener(
            @Parameter(description = "ID del proveedor a obtener", example = "1")
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        ProveedorResponse proveedor = proveedorService.obtener(id, token);
        EntityModel<ProveedorResponse> recurso = EntityModel.of(proveedor);

        recurso.add(linkTo(methodOn(ProveedorController.class).obtener(id, token)).withSelfRel());
        recurso.add(linkTo(methodOn(ProveedorController.class).listar(token)).withRel("all"));
        recurso.add(linkTo(methodOn(ProveedorController.class).actualizar(id, null, token)).withRel("update"));
        recurso.add(linkTo(methodOn(ProveedorController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<ProveedorResponse>>builder()
                        .success(true)
                        .message("Proveedor Obtenido")
                        .data(recurso)
                        .build()
        );
    }

    @Operation(summary = "Actualizar proveedor", description = "Actualiza los datos de un proveedor existente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Proveedor actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProveedorResponse>> actualizar(
            @Parameter(description = "ID del proveedor a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ProveedorDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<ProveedorResponse>builder()
                        .success(true)
                        .message("Proveedor Actualizado")
                        .data(proveedorService.actualizar(id, dto, token))
                        .build()
        );
    }

    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor por ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Proveedor eliminado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @Parameter(description = "ID del proveedor a eliminar", example = "1")
            @PathVariable Long id) {

        proveedorService.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Proveedor Eliminado")
                        .build()
        );
    }
}