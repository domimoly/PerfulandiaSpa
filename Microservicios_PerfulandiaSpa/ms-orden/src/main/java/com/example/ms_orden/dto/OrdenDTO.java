package com.example.ms_orden.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrdenDTO {
    
    @NotBlank(message= "El número de orden es obligatorio")
    private int numeroOrden;
    
    @NotNull(message= "La fecha de creación es obligatoria")
    private String fechaCreacion;
    
    @NotNull(message= "La fecha de recepción es obligatoria")
    private String fechaRecibida;
    
    @NotNull(message = "El total es obligatorio")
    @Min(value = 0, message = "El total debe ser positivo")
    private double total;
    
    @NotNull(message = "El descuento aplicado es obligatorio")
    @Min(value = 0, message = "El descuento aplicado debe ser positivo")
    private double descuentoAplicado;
}
