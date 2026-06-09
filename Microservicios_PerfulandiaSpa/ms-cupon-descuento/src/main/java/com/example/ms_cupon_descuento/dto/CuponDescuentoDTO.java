package com.example.ms_cupon_descuento.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuponDescuentoDTO {

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoria;

    @NotBlank(message = "El código del cupón es obligatorio")
    private String codigo;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Min(value = 1, message = "El porcentaje de descuento debe ser al menos 1%")
    @Max(value = 100, message = "El porcentaje de descuento no puede ser mayor a 100%")
    private Double porcentajeDescuento;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Future(message = "La fecha de vencimiento debe ser una fecha futura")
    private LocalDate fechaVencimiento;

    private Boolean activo;
}