package com.example.ms_ticket_soporte.controller;

import java.util.List;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketSoporteController {

    private final TicketSoporteService tsService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<TicketSoporteResponse>> crear(@Valid @RequestBody TicketSoporteDTO dto, @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<TicketSoporteResponse>builder()
                        .success(true)
                        .message("Ticket creado")
                        .data(tsService.crear(dto, token))
                        .build()
        );
    }  

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<TicketSoporteResponse>>> listar(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<List<TicketSoporteResponse>>builder()
                        .success(true)
                        .data(tsService.listar(token))
                        .build()
        );
    }

        @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<TicketSoporteResponse>> obtener(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<TicketSoporteResponse>builder()
                        .success(true)
                        .data(tsService.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<TicketSoporteResponse>> actualizar(@PathVariable Long id,@Valid @RequestBody TicketSoporteDTO dto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<TicketSoporteResponse>builder()
                        .success(true)
                        .message("Ticket actualizado")
                        .data(tsService.actualizar(id, dto, token))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        tsService.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Ticket eliminado")
                        .build()
        );
    }    

}
