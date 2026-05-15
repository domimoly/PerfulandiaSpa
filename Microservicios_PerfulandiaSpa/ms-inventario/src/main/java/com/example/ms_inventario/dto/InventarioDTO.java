package com.example.ms_inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioDTO {
    @NotNull(message = "El ID del producto es obligatorio")
    private Long producto;

    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Long sucursal;

    @NotNull(message = "La cantidad en stock es obligatoria")
    @Min(value = 0, message = "La cantidad en inventario no puede ser menor a 0")
    private Integer cantidad;
}
