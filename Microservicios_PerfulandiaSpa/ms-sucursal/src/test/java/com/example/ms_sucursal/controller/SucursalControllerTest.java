package com.example.ms_sucursal.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.ms_sucursal.dto.SucursalDTO;
import com.example.ms_sucursal.model.Sucursal;
import com.example.ms_sucursal.service.SucursalService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SucursalController.class)
@AutoConfigureMockMvc(addFilters = false)
class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SucursalService service;

    @Test
    void debeListarSucursales() throws Exception {
        List<Sucursal> sucursales = List.of(
                new Sucursal(null, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias")
        );

        when(service.listar()).thenReturn(sucursales);

        mockMvc.perform(get("/api/v2/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].nombre").value("Barrio Meiggs"))
                .andExpect(jsonPath("$.data[0].direccion").value("Barrio Meiggs, Santiago"))
                .andExpect(jsonPath("$.data[0].horarioApertura").value("Lun-Sab 9:00-20:00"))
                .andExpect(jsonPath("$.data[0].politicas").value("Devolucion con boleta en 30 dias"));
    }

    @Test
    void debeObtenerSucursalPorId() throws Exception {
        Sucursal sucursal = new Sucursal(1L, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias");

        when(service.obtener(1L)).thenReturn(sucursal);

        mockMvc.perform(get("/api/v2/sucursales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Sucursal obtenida"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nombre").value("Barrio Meiggs"))
                .andExpect(jsonPath("$.data.direccion").value("Barrio Meiggs, Santiago"))
                .andExpect(jsonPath("$.data.horarioApertura").value("Lun-Sab 9:00-20:00"))
                .andExpect(jsonPath("$.data.politicas").value("Devolucion con boleta en 30 dias"));
    }

    @Test
    void debeCrearSucursal() throws Exception {
        SucursalDTO dto = new SucursalDTO();
        dto.setNombre("Barrio Meiggs");
        dto.setDireccion("Barrio Meiggs, Santiago");
        dto.setHorarioApertura("Lun-Sab 9:00-20:00");
        dto.setPoliticas("Devolucion con boleta en 30 dias");

        Sucursal creada = new Sucursal(1L, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias");

        when(service.crear(any(SucursalDTO.class))).thenReturn(creada);

        mockMvc.perform(post("/api/v2/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Sucursal creada"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nombre").value("Barrio Meiggs"))
                .andExpect(jsonPath("$.data.direccion").value("Barrio Meiggs, Santiago"))
                .andExpect(jsonPath("$.data.horarioApertura").value("Lun-Sab 9:00-20:00"))
                .andExpect(jsonPath("$.data.politicas").value("Devolucion con boleta en 30 dias"));
    }

    @Test
    void debeActualizarSucursal() throws Exception {
        SucursalDTO dto = new SucursalDTO();
        dto.setNombre("Barrio Meiggs");
        dto.setDireccion("Barrio Meiggs, Santiago");
        dto.setHorarioApertura("Lun-Sab 9:00-20:00");
        dto.setPoliticas("Devolucion con boleta en 30 dias");

        Sucursal actualizada = new Sucursal(1L, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias");

        when(service.actualizar(eq(1L), any(SucursalDTO.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v2/sucursales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Sucursal actualizada"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nombre").value("Barrio Meiggs"))
                .andExpect(jsonPath("$.data.direccion").value("Barrio Meiggs, Santiago"))
                .andExpect(jsonPath("$.data.horarioApertura").value("Lun-Sab 9:00-20:00"))
                .andExpect(jsonPath("$.data.politicas").value("Devolucion con boleta en 30 dias"));
    }

    @Test
    void debeEliminarSucursal() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/v2/sucursales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Sucursal eliminada"));
    }
}
