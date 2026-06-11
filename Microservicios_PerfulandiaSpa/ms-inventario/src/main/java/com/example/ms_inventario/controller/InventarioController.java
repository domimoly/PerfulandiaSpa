package com.example.ms_inventario.controller;

import java.util.List;

// Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

// HATEOAAS
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

import com.example.ms_inventario.dto.ApiResponse;
import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.dto.InventarioResponse;
import com.example.ms_inventario.service.InventarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Inventario", description = "Gestión de inventario de PerfulandiaSpa")
@RestController
@RequestMapping("/api/v2/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService invService;

    @Operation(summary = "Crear registro de inventario", description = "Crea un nuevo registro de inventario asociado a producto, sucursal y proveedor")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Inventario creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<InventarioResponse>> crear(
            @Valid @RequestBody InventarioDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<InventarioResponse>builder()
                        .success(true)
                        .message("Inventario Creado")
                        .data(invService.crear(dto, token))
                        .build()
        );
    }

    @Operation(summary = "Listar inventario", description = "Retorna todos los registros de inventario")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<InventarioResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<InventarioResponse>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(invService.listar(token))
                        .build()
        );
    }

    @Operation(summary = "Obtener inventario", description = "Retorna un registro de inventario por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inventario obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Inventario no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<EntityModel<InventarioResponse>>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        InventarioResponse inventario = invService.obtener(id, token);
        EntityModel<InventarioResponse> recurso = EntityModel.of(inventario);

        recurso.add(linkTo(methodOn(InventarioController.class).obtener(id, token)).withSelfRel());
        recurso.add(linkTo(methodOn(InventarioController.class).listar(token)).withRel("all"));
        recurso.add(linkTo(methodOn(InventarioController.class).actualizar(id, null, token)).withRel("update"));
        recurso.add(linkTo(methodOn(InventarioController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<InventarioResponse>>builder()
                        .success(true)
                        .message("Inventario obtenido")
                        .data(recurso)
                        .build()
        );
    }

    @Operation(summary = "Actualizar inventario", description = "Actualiza un registro de inventario existente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inventario actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Inventario no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<InventarioResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody InventarioDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<InventarioResponse>builder()
                        .success(true)
                        .message("Inventario actualizado")
                        .data(invService.actualizar(id, dto, token))
                        .build()
        );
    }

    @Operation(summary = "Eliminar inventario", description = "Elimina un registro de inventario por ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inventario eliminado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Inventario no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
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