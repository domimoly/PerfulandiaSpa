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
}
