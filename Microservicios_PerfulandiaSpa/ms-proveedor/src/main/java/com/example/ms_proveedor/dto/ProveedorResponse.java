package com.example.ms_proveedor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProveedorResponse {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private SucursalResponse sucursal;
}