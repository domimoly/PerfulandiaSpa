package com.example.ms_inventario.dto;

import lombok.Data;

@Data
public class ProductoReponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
}
