package com.example.ms_cupon_descuento.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuponResponse {
    private String codigo;
    private Double porcentajeDescuento;
    private LocalDate fechaVencimiento;
    /*Al momento de poner la id de producto, arrojara si tiene un
    cupón activo, mostrando los datos de este, si no, aparecerá como false - no aplica */
}
