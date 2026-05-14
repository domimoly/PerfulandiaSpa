package com.example.ms_cupon_descuento.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuponResponse {
    private Long id;
    private ProductoReponse productoId;
    private String nombreProducto; 
    private String codigo;
    private Double porcentajeDescuento;
    private LocalDate fechaVencimiento;
    private Boolean activo;
    /*Al momento de poner la id de producto, arrojara si tiene un
    cupón activo, mostrando los datos de este, si no, aparecerá como false - no aplica */
}
