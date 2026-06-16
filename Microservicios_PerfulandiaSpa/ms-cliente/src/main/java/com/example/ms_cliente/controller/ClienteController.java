package com.example.ms_cliente.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_cliente.dto.ApiResponse;
import com.example.ms_cliente.dto.ClienteDTO;
import com.example.ms_cliente.dto.ClienteResponse;
import com.example.ms_cliente.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Clientes", description = "Operaciones relacionadas con la gestión de clientes")
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(
        summary = "Crear un nuevo cliente",
        description = "Registra un cliente en el sistema. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClienteResponse>> crear(
            @Valid @RequestBody ClienteDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<ClienteResponse>builder()
                        .success(true)
                        .message("Cliente creado")
                        .data(clienteService.crear(dto, token))
                        .build()
        );
    }

    @Operation(
        summary = "Listar todos los clientes",
        description = "Retorna una lista con todos los clientes registrados. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<ClienteResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<ClienteResponse>>builder()
                        .success(true)
                        .data(clienteService.listar(token))
                        .build()
        );
    }

    @Operation(
        summary = "Obtener cliente por ID",
        description = "Busca los datos de un cliente mediante su identificador único. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<EntityModel<ClienteResponse>>> obtener(
            @Parameter(description = "ID del cliente a buscar", example = "1")
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        ClienteResponse cliente = clienteService.obtener(id, token);
        EntityModel<ClienteResponse> recurso = EntityModel.of(cliente);

        recurso.add(linkTo(methodOn(ClienteController.class).obtener(id, token)).withSelfRel());
        recurso.add(linkTo(methodOn(ClienteController.class).listar(token)).withRel("all"));
        recurso.add(linkTo(methodOn(ClienteController.class).actualizar(id, null, token)).withRel("update"));
        recurso.add(linkTo(methodOn(ClienteController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<ClienteResponse>>builder()
                        .success(true)
                        .data(recurso)
                        .build()
        );
    }

    @Operation(
        summary = "Actualizar cliente por ID",
        description = "Modifica los datos existentes de un cliente. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos enviados inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClienteResponse>> actualizar(
            @Parameter(description = "ID del cliente a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<ClienteResponse>builder()
                        .success(true)
                        .message("Cliente actualizado")
                        .data(clienteService.actualizar(id, dto, token))
                        .build()
        );
    }

    @Operation(
        summary = "Eliminar cliente por ID",
        description = "Borra físicamente a un cliente del sistema. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cliente eliminado con éxito"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @Parameter(description = "ID del cliente a eliminar", example = "1")
            @PathVariable Long id) {

        clienteService.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Cliente eliminado")
                        .build()
        );
    }

}

