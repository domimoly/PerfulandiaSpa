package com.example.ms_cupon_descuento.controller;

import java.util.List;

// Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

// HATEOAS
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_cupon_descuento.dto.ApiResponse;
import com.example.ms_cupon_descuento.dto.CuponDescuentoDTO;
import com.example.ms_cupon_descuento.dto.CuponResponse;
import com.example.ms_cupon_descuento.service.CuponDescuentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Cupones de Descuento", description = "Gestión de cupones de descuento de PerfulandiaSpa")
@RestController
@RequestMapping("/api/v2/cupones")
@RequiredArgsConstructor
public class CuponDescuentoController {

    private final CuponDescuentoService cuponService;

    @Operation(summary = "Crear cupón", description = "Crea un nuevo cupón de descuento asociado a una categoría")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Cupón creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CuponResponse>> crear(
            @Valid @RequestBody CuponDescuentoDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<CuponResponse>builder()
                        .success(true)
                        .message("Cupón Creado")
                        .data(cuponService.crear(dto, token))
                        .build()
        );
    }

    @Operation(summary = "Listar cupones", description = "Retorna todos los cupones de descuento disponibles")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<CuponResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<CuponResponse>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(cuponService.listar(token))
                        .build()
        );
    }

    @Operation(summary = "Obtener cupón", description = "Retorna un cupón de descuento por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cupón obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cupón no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<EntityModel<CuponResponse>>> obtener(
            @Parameter(description = "ID del cupón a obtener", example = "1")
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        CuponResponse cupon = cuponService.obtener(id, token);
        EntityModel<CuponResponse> recurso = EntityModel.of(cupon);

        recurso.add(linkTo(methodOn(CuponDescuentoController.class).obtener(id, token)).withSelfRel());
        recurso.add(linkTo(methodOn(CuponDescuentoController.class).listar(token)).withRel("all"));
        recurso.add(linkTo(methodOn(CuponDescuentoController.class).actualizar(id, null, token)).withRel("update"));
        recurso.add(linkTo(methodOn(CuponDescuentoController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<CuponResponse>>builder()
                        .success(true)
                        .message("Cupón obtenido")
                        .data(recurso)
                        .build()
        );
    }

    @Operation(summary = "Actualizar cupón", description = "Actualiza los datos de un cupón existente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cupón actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cupón no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CuponResponse>> actualizar(
            @Parameter(description = "ID del cupón a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CuponDescuentoDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<CuponResponse>builder()
                        .success(true)
                        .message("Cupón actualizado")
                        .data(cuponService.actualizar(id, dto, token))
                        .build()
        );
    }

    @Operation(summary = "Eliminar cupón", description = "Elimina un cupón de descuento por ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cupón eliminado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cupón no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @Parameter(description = "ID del producto a eliminar", example = "1")
            @PathVariable Long id) {

        cuponService.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Cupón eliminado")
                        .build()
        );
    }
}