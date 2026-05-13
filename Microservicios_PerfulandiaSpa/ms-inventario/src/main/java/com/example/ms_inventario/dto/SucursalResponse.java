package com.example.ms_inventario.dto;

import lombok.Data;

@Data
public class SucursalResponse {
    private Long id;
    private String nombre;
    private String direccion;
    private String horarioApertura;
    private String politicas;
}
