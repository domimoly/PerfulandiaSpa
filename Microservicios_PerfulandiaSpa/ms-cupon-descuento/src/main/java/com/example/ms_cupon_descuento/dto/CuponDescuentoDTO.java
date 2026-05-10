package com.example.ms_cupon_descuento.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CuponDescuentoDTO {

    @NotBlank(message = "El código del cupón es obligatorio")
    private String codigo;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Min(value = 1, message = "El porcentaje de descuento debe ser al menos 1%")
    @Max(value = 100, message = "El porcentaje de descuento no puede ser mayor a 100%")
    private Double porcentajeDescuento;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Future(message = "La fecha de vencimiento debe ser una fecha futura")
    private LocalDate fechaVencimiento;
}
