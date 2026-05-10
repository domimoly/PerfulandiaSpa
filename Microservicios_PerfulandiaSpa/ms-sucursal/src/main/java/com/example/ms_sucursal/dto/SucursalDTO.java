package com.example.ms_sucursal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SucursalDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El horario de apertura es obligatorio")
    private String horarioApertura;

    @NotBlank(message = "Las políticas son obligatorias")
    private String politicas;
}
