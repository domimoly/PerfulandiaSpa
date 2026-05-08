package com.example.ms_producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

    private Long id;

    // Las notaciones son de guía, según lo enseñado en clase, se aplican en otras
    // partes
    //@NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    //@NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    //@NotNull(message = "El precio es obligatorio")
    //@Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;
}
