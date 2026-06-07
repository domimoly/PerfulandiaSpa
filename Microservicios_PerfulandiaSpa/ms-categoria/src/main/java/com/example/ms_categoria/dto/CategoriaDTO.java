package com.example.ms_categoria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoriaDTO {
    
    @NotBlank(message= "El nombre de la categoría es obligatorio")
    private String nombre;
    
    @NotNull(message= "La descripción es obligatoria")
    private String descripcion;
}