package com.example.ms_inventario.dto;

import lombok.Data;

@Data
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Integer cantidad;
}
