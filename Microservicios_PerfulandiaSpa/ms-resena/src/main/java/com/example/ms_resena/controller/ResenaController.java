package com.example.ms_resena.controller;

import java.util.List;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.ms_resena.dto.*;
import com.example.ms_resena.service.ResenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Reseñas", description = "Operaciones relacionadas con la gestión de reseñas de productos")
@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @Operation(
        summary = "Crear una nueva reseña",
        description = "Registra una reseña de producto. Un usuario solo puede reseñar un producto una vez. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Reseña creada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<ResenaResponse>> crear(
            @Valid @RequestBody ResenaDTO dto,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(201).body(
                ApiResponse.<ResenaResponse>builder()
                        .success(true)
                        .message("Reseña creada")
                        .data(resenaService.crear(dto, token))
                        .build()
        );
    }

    @Operation(
        summary = "Listar todas las reseñas",
        description = "Retorna una lista con todas las reseñas registradas. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<ResenaResponse>>> listar(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<List<ResenaResponse>>builder()
                        .success(true)
                        .data(resenaService.listar(token))
                        .build()
        );
    }

    @Operation(
        summary = "Obtener reseña por ID",
        description = "Busca una reseña mediante su identificador único. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reseña encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<EntityModel<ResenaResponse>>> obtener(
            @Parameter(description = "ID de la reseña a buscar", example = "1")
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        ResenaResponse resena = resenaService.obtener(id, token);
        EntityModel<ResenaResponse> recurso = EntityModel.of(resena);

        recurso.add(linkTo(methodOn(ResenaController.class).obtener(id, token)).withSelfRel());
        recurso.add(linkTo(methodOn(ResenaController.class).listar(token)).withRel("all"));
        recurso.add(linkTo(methodOn(ResenaController.class).actualizar(id, null, token)).withRel("update"));
        recurso.add(linkTo(methodOn(ResenaController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<ResenaResponse>>builder()
                        .success(true)
                        .data(recurso)
                        .build()
        );
    }

    @Operation(
        summary = "Actualizar reseña por ID",
        description = "Modifica los datos de una reseña existente. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos enviados inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<ResenaResponse>> actualizar(
            @Parameter(description = "ID de la reseña a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ResenaDTO dto,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<ResenaResponse>builder()
                        .success(true)
                        .message("Reseña actualizada")
                        .data(resenaService.actualizar(id, dto, token))
                        .build()
        );
    }

    @Operation(
        summary = "Eliminar reseña por ID",
        description = "Borra físicamente una reseña del sistema. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reseña eliminada con éxito"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @Parameter(description = "ID de la reseña a eliminar", example = "1")
            @PathVariable Long id) {
        resenaService.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Reseña eliminada")
                        .build()
        );
    }
}
