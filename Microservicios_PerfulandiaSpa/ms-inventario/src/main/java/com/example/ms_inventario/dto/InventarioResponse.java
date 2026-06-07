package com.example.ms_inventario.dto;

import java.time.LocalDate;

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
    private ProductoResponse producto;
    private SucursalResponse sucursal; 
    private Integer cantidad; 
    private Integer stockMinimo;
    private LocalDate fechaInventario;
    private ProveedorResponse proveedor;
}
