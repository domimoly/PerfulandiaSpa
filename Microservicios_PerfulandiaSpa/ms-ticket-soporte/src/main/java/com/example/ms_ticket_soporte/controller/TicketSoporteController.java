package com.example.ms_ticket_soporte.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
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

import com.example.ms_ticket_soporte.dto.ApiResponse;
import com.example.ms_ticket_soporte.dto.TicketSoporteDTO;
import com.example.ms_ticket_soporte.dto.TicketSoporteResponse;
import com.example.ms_ticket_soporte.service.TicketSoporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Tickets de Soporte", description = "Operaciones relacionadas con la gestión de tickets de soporte")
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketSoporteController {

    private final TicketSoporteService tsService;

    @Operation(
        summary = "Crear un nuevo ticket",
        description = "Registra un ticket de soporte en el sistema. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Ticket creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<TicketSoporteResponse>> crear(
            @Valid @RequestBody TicketSoporteDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<TicketSoporteResponse>builder()
                        .success(true)
                        .message("Ticket creado")
                        .data(tsService.crear(dto, token))
                        .build()
        );
    }

    @Operation(
        summary = "Listar todos los tickets",
        description = "Retorna una lista con todos los tickets de soporte. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<TicketSoporteResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<TicketSoporteResponse>>builder()
                        .success(true)
                        .data(tsService.listar(token))
                        .build()
        );
    }

    @Operation(
        summary = "Obtener ticket por ID",
        description = "Busca un ticket de soporte mediante su identificador único. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ticket encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<EntityModel<TicketSoporteResponse>>> obtener(
            @Parameter(description = "ID del ticket a buscar", example = "1")
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        TicketSoporteResponse ticket = tsService.obtener(id, token);
        EntityModel<TicketSoporteResponse> recurso = EntityModel.of(ticket);

        recurso.add(linkTo(methodOn(TicketSoporteController.class).obtener(id, token)).withSelfRel());
        recurso.add(linkTo(methodOn(TicketSoporteController.class).listar(token)).withRel("all"));
        recurso.add(linkTo(methodOn(TicketSoporteController.class).actualizar(id, null, token)).withRel("update"));
        recurso.add(linkTo(methodOn(TicketSoporteController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<TicketSoporteResponse>>builder()
                        .success(true)
                        .data(recurso)
                        .build()
        );
    }

    @Operation(
        summary = "Actualizar ticket por ID",
        description = "Modifica los datos de un ticket de soporte existente. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ticket actualizado correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos enviados inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<TicketSoporteResponse>> actualizar(
            @Parameter(description = "ID del ticket a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody TicketSoporteDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<TicketSoporteResponse>builder()
                        .success(true)
                        .message("Ticket actualizado")
                        .data(tsService.actualizar(id, dto, token))
                        .build()
        );
    }

    @Operation(
        summary = "Eliminar ticket por ID",
        description = "Borra físicamente un ticket de soporte del sistema. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ticket eliminado con éxito"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @Parameter(description = "ID del ticket a eliminar", example = "1")
            @PathVariable Long id) {

        tsService.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Ticket eliminado")
                        .build()
        );
    }

}
