package com.example.ms_usuario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.ms_usuario.dto.ApiResponse;
import com.example.ms_usuario.dto.UsuarioDTO;
import com.example.ms_usuario.model.Usuario;
import com.example.ms_usuario.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios y roles")
@RestController
@RequestMapping("/api/v2/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService UsService;

    @Operation(
        summary = "Crear un nuevo usuario",
        description = "Registra un usuario en el sistema. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Usuario>> crear(@Valid @RequestBody UsuarioDTO dto) {
        Usuario usuario = UsService.crear(dto);
        return ResponseEntity.status(201).body(
                ApiResponse.<Usuario>builder()
                .success(true)
                .message("Usuario creado")
                .data(usuario)
                .build()
        );
    }     

    @Operation(
        summary = "Listar todos los usuarios",
        description = "Retorna una lista con todos los usuarios registrados. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<Usuario>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Usuario>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(UsService.listar())
                        .build()
        );
    }

    @Operation(
        summary = "Obtener usuario por ID",
        description = "Busca los datos de un usuario mediante su identificador único. Requiere rol USER o ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<EntityModel<Usuario>>> obtener(
            @Parameter(description = "ID del usuario a buscar", example = "1")
            @PathVariable Long id) {

        Usuario usuario = UsService.obtener(id);
        EntityModel<Usuario> recurso = EntityModel.of(usuario);

        recurso.add(linkTo(methodOn(UsuarioController.class).obtener(id)).withSelfRel());
        recurso.add(linkTo(methodOn(UsuarioController.class).listar()).withRel("all"));
        recurso.add(linkTo(methodOn(UsuarioController.class).actualizar(id, null)).withRel("update"));
        recurso.add(linkTo(methodOn(UsuarioController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<Usuario>>builder()
                    .success(true)
                    .message("Usuario obtenido")
                    .data(recurso)
                    .build()
        );
    }

    @Operation(
        summary = "Actualizar usuario por ID",
        description = "Modifica los datos existentes de un usuario. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos enviados inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Usuario>> actualizar(
            @Parameter(description = "ID del usuario a actualizar", example = "1")
            @PathVariable Long id, 
            @Valid @RequestBody UsuarioDTO dto) {

        Usuario usuario = UsService.actualizar(id, dto);
        return ResponseEntity.ok(
                ApiResponse.<Usuario>builder()
                        .success(true)
                        .message("Usuario actualizado")
                        .data(usuario)
                        .build()
        );
    }

    @Operation(
        summary = "Eliminar usuario por ID",
        description = "Borra físicamente a un usuario del sistema de forma permanente. Requiere rol ADMIN."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario eliminado con éxito"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @Parameter(description = "ID del usuario a eliminar", example = "1")
            @PathVariable Long id) {
        UsService.eliminar(id);
        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .success(true)
                .message("Usuario eliminado")
                .build()
        );
    }

}
