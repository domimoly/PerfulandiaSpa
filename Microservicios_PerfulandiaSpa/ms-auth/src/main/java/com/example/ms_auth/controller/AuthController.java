package com.example.ms_auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_auth.dto.AuthResponse;
import com.example.ms_auth.dto.LoginRequest;
import com.example.ms_auth.dto.RefreshRequest;
import com.example.ms_auth.dto.RegisterRequest;
import com.example.ms_auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Autenticación", description = "Operaciones de registro, login y renovación de token")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService service;

    @Operation(
            summary = "Registrar un usuario",
            description = "Genera credenciales para un nuevo usuario"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login exitoso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/register")
    public ResponseEntity<com.example.ms_auth.dto.ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest req) {
        log.info("POST /auth/register - usuario: {}", req.getUsername());

        AuthResponse res = service.register(req);

        return ResponseEntity.ok(
                com.example.ms_auth.dto.ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Usuario registrado")
                        .data(res)
                        .build()
        );
    }

    @Operation(
            summary = "Iniciar sesión",
            description = "Valida credenciales y retorna accessToken y refreshToken"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login exitoso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<com.example.ms_auth.dto.ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest req) {

        log.info("POST /auth/login - usuario: {}", req.getUsername());
        AuthResponse res = service.login(req);

        return ResponseEntity.ok(
                com.example.ms_auth.dto.ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Login exitoso")
                        .data(res)
                        .build()
        );
    }

    @Operation(
            summary = "Refrescar token",
            description = "Renueva el token de acceso usando el refreshToken"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login exitoso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/refresh")
    public ResponseEntity<com.example.ms_auth.dto.ApiResponse<AuthResponse>> refresh(@RequestBody RefreshRequest req) {

        AuthResponse res = service.refresh(req.getRefreshToken());

        return ResponseEntity.ok(
                com.example.ms_auth.dto.ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Token renovado")
                        .data(res)
                        .build()
        );
    }
}