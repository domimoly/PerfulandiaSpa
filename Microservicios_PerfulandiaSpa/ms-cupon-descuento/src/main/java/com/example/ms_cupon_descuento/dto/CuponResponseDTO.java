package com.example.ms_cupon_descuento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuponResponseDTO {
    private String codigo;
    private double porcentajeDescuento;
}
