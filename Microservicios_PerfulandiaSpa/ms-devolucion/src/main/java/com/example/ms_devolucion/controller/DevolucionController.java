package com.example.ms_devolucion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.ms_devolucion.dto.ApiResponse;
import com.example.ms_devolucion.dto.DevolucionDTO;
import com.example.ms_devolucion.model.Devolucion;
import com.example.ms_devolucion.service.DevolucionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Devoluciones", description = "Operaciones relacionadas con devoluciones")
@RestController
@RequestMapping("/api/v2/devoluciones")
@RequiredArgsConstructor
public class DevolucionController {

    private final DevolucionService devolucionService;

    @Operation(
        summary = "Crear devoluciones",
        description = "Crea una nueva devolución. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Devolución creada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Devolucion>> crear(@Valid @RequestBody DevolucionDTO dto) {

        Devolucion devolucion = devolucionService.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Devolucion>builder()
                        .success(true)
                        .message("Devolución generada")
                        .data(devolucion)
                        .build()
        );
    }

    @Operation(
        summary = "Listar devoluciones",
        description = "Retorna todos las devoluciones registradas. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Devolucion>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Devolucion>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(devolucionService.listar())
                        .build()
        );
    }

    @Operation(
        summary = "Obtener devolución por ID",
        description = "Busca una devolución usando su identificador. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Devolución obtenida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Devolución no encontrada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Devolucion>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Devolucion>builder()
                        .success(true)
                        .message("Devolución obtenida")
                        .data(devolucionService.obtener(id))
                        .build()
        );
    }

    @Operation(
    summary = "Actualizar devolución por ID",
    description = "Actualiza una devolución usando su identificador. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Devolución actualizada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Devolución no encontrada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Devolucion>> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody DevolucionDTO dto) {

        Devolucion devolucion = devolucionService.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Devolucion>builder()
                        .success(true)
                        .message("Devolución actualizada")
                        .data(devolucion)
                        .build()
        );
    }

    @Operation(
    summary = "Eliminar devolución por ID",
    description = "Elimina una devolución usando su identificador. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Devolución eliminada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Devolución no encontrada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        devolucionService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Devolución eliminada")
                        .build()
        );
    }
}
