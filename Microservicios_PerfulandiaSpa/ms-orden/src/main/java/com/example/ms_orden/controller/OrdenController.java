package com.example.ms_orden.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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

import com.example.ms_orden.dto.ApiResponse;
import com.example.ms_orden.dto.OrdenDTO;
import com.example.ms_orden.model.Orden;
import com.example.ms_orden.service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Ordenes", description = "Operaciones relacionadas con ordenes")
@RestController
@RequestMapping("/api/v2/ordenes")
@RequiredArgsConstructor
public class OrdenController {
    private final OrdenService ordenService;

    @Operation(
        summary = "Crear orden",
        description = "Crea una nueva orden. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Orden creada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Orden>> crear(@Valid @RequestBody OrdenDTO dto) {
        
        Orden orden = ordenService.crear(dto);
        return ResponseEntity.status(201).body(
                ApiResponse.<Orden>builder()
                        .success(true)
                        .message("Orden Creada")
                        .data(orden)
                        .build()
        );
    }

    @Operation(
        summary = "Listar ordenes",
        description = "Retorna todas las ordenes registradas. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Orden>>> listar() {

    return ResponseEntity.ok(
            ApiResponse.<List<Orden>>builder()
                    .success(true)
                    .message("Listado obtenido")
                    .data(ordenService.listar())
                    .build()
        );
    }

    @Operation(
        summary = "Obtener orden por ID",
        description = "Busca una orden usando su identificador. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Orden obtenida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Orden no encontrada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<EntityModel<Orden>>> obtener(@PathVariable Long id) {

        Orden orden = ordenService.obtener(id);

    EntityModel<Orden> recurso = EntityModel.of(orden);

    recurso.add(
            linkTo(methodOn(OrdenController.class).obtener(id))
                    .withSelfRel()
    );

    recurso.add(
            linkTo(methodOn(OrdenController.class).listar())
                    .withRel("all")
    );

    recurso.add(
            linkTo(methodOn(OrdenController.class).actualizar(id, null))
                    .withRel("update")
    );

    recurso.add(
            linkTo(methodOn(OrdenController.class).eliminar(id))
                    .withRel("delete")
    );


        return ResponseEntity.ok(
                ApiResponse.<EntityModel<Orden>>builder()
                        .success(true)
                        .message("Orden obtenida")
                        .data(recurso)
                        .build()
        );
    }

    @Operation(
    summary = "Actualizar orden por ID",
    description = "Actualiza una orden usando su identificador. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Orden actualizada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Orden no encontrada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Orden>> actualizar(@PathVariable Long id, @Valid @RequestBody OrdenDTO dto) {

        Orden orden = ordenService.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Orden>builder()
                        .success(true)
                        .message("Orden actualizada")
                        .data(orden)
                        .build()
        );
    }

    @Operation(
    summary = "Eliminar orden por ID",
    description = "Elimina una orden usando su identificador. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Orden eliminada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Orden no encontrada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        ordenService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Orden eliminada")
                        .build()
        );
    }
}
