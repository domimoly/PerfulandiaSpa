package com.example.ms_inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventarioResponse {
    private Long id;
    private ProductoReponse productoId;
    private SucursalResponse sucursalId;
    private Integer cantidad; 
}
