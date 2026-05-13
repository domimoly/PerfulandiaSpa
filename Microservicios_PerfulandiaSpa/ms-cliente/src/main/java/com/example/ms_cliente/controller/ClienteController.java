package com.example.ms_cliente.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_cliente.dto.ApiResponse;
import com.example.ms_cliente.dto.ClienteDTO;
import com.example.ms_cliente.dto.ClienteResponse;
import com.example.ms_cliente.service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClienteResponse>> crear(@Valid @RequestBody ClienteDTO dto, @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                
            ApiResponse.<ClienteResponse>builder()

                        .success(true)
                        .message("Cliente creado")
                        .data(clienteService.crear(dto, token))
                        .build()
        );
    }  

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<ClienteResponse>>> listar(@RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<ClienteResponse>>builder()
                        .success(true)
                        .data(clienteService.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<ClienteResponse>> obtener(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<ClienteResponse>builder()
                        .success(true)
                        .data(clienteService.obtener(id, token))
                        .build()
        );
    }  

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClienteResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<ClienteResponse>builder()
                        .success(true)
                        .message("Cliente actualizado")
                        .data(clienteService.actualizar(id, dto, token))
                        .build()
        );
    } 
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {clienteService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Cliente eliminado")
                        .build()
        );
    }


}

