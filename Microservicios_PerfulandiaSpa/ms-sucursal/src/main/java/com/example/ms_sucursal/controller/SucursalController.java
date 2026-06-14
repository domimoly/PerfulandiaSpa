package com.example.ms_sucursal.controller;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.ms_sucursal.dto.ApiResponse;
import com.example.ms_sucursal.dto.SucursalDTO;
import com.example.ms_sucursal.model.Sucursal;
import com.example.ms_sucursal.service.SucursalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Sucursales", description = "Operaciones relacionadas con sucursales")
@RestController
@RequestMapping("/api/v2/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalService sucursalService;

    @Operation(
    summary = "Crear sucursal",
    description = "Crea una nueva sucursal. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Sucursal creada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Sucursal>> crear(@Valid @RequestBody SucursalDTO dto) {

        Sucursal sucursal = sucursalService.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Sucursal>builder()
                        .success(true)
                        .message("Sucursal creada")
                        .data(sucursal)
                        .build()
        );
    }

    @Operation(
        summary = "Listar sucursales",
        description = "Retorna todas las sucursales registradas. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Sucursal>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Sucursal>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(sucursalService.listar())
                        .build()
        );
    }

    @Operation(
    summary = "Obtener sucursal por ID",
    description = "Busca una sucursal usando su identificador. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Sucursal obtenida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Sucursal>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Sucursal>builder()
                        .success(true)
                        .message("Sucursal obtenida")
                        .data(sucursalService.obtener(id))
                        .build()
        );
    }

    @Operation(
    summary = "Actualizar sucursal por ID",
    description = "Actualiza una sucursal usando su identificador. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Sucursal actualizada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Sucursal>> actualizar(@PathVariable Long id, @Valid @RequestBody SucursalDTO dto) {

        Sucursal sucursal = sucursalService.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Sucursal>builder()
                        .success(true)
                        .message("Sucursal actualizada")
                        .data(sucursal)
                        .build()
        );
    }

    @Operation(
    summary = "Eliminar sucursal por ID",
    description = "Elimina una sucursal usando su identificador. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Sucursal eliminada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        sucursalService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Sucursal eliminada")
                        .build()
        );
    }
}
