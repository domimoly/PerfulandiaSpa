package com.example.ms_usuario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_usuario.dto.ApiResponse;
import com.example.ms_usuario.dto.UsuarioDTO;
import com.example.ms_usuario.model.Usuario;
import com.example.ms_usuario.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService UsService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Usuario>> crear(@Valid@RequestBody UsuarioDTO dto) {
        
        Usuario usuario = UsService.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Usuario>builder()
                .success(true)
                .message("Usuario creado")
                .data(usuario)
                .build()
        );

    }     

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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Usuario>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Usuario>builder()
                    .success(true)
                    .message("Usuario obtenido")
                    .data(UsService.obtener(id))
                    .build()
        );
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Usuario>> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {

        Usuario usuario = UsService.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Usuario>builder()
                        .success(true)
                        .message("Usuario actualizado")
                        .data(usuario)
                        .build()
        );
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        UsService.eliminar(id);
        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .success(true)
                .message("Usuario eliminado")
                .build()
        );
    }

}
