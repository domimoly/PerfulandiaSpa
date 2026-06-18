package com.example.ms_orden.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.ms_orden.dto.OrdenDTO;
import com.example.ms_orden.model.Orden;
import com.example.ms_orden.security.JwtUtil;
import com.example.ms_orden.service.OrdenService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(OrdenController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrdenService service;

    @MockitoBean
    private JwtUtil jwtUtil;
    // Agregar para funcionamiento de cada Test
    @TestConfiguration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Test
    void debeListarOrdenes() throws Exception {
        List<Orden> ordenes = List.of(
                new Orden(1L, 1, "2026-03-20", "2026-03-23", 115000, 0)
        );

        when(service.listar()).thenReturn(ordenes);

        mockMvc.perform(get("/api/v2/ordenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].numeroOrden").value(1))
                .andExpect(jsonPath("$.data[0].fechaCreacion").value("2026-03-20"))
                .andExpect(jsonPath("$.data[0].fechaRecibida").value("2026-03-23"))
                .andExpect(jsonPath("$.data[0].total").value(115000))
                .andExpect(jsonPath("$.data[0].descuentoAplicado").value(0));
    }

    @Test
    void debeObtenerOrdenPorId() throws Exception {
        Orden orden = new Orden(1L, 1, "2026-03-20", "2026-03-23", 115000, 0);

        when(service.obtener(1L)).thenReturn(orden);

        mockMvc.perform(get("/api/v2/ordenes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Orden obtenida"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.numeroOrden").value(1))
                .andExpect(jsonPath("$.data.fechaCreacion").value("2026-03-20"))
                .andExpect(jsonPath("$.data.fechaRecibida").value("2026-03-23"))
                .andExpect(jsonPath("$.data.total").value(115000))
                .andExpect(jsonPath("$.data.descuentoAplicado").value(0));
    }

    @Test
    void debeCrearOrden() throws Exception {
        OrdenDTO dto = new OrdenDTO();
        dto.setNumeroOrden(1);
        dto.setFechaCreacion("2026-03-20");
        dto.setFechaRecibida("2026-03-23");
        dto.setTotal(115000);
        dto.setDescuentoAplicado(0);

        Orden creada = new Orden(1L, 1, "2026-03-20", "2026-03-23", 115000, 0);

        when(service.crear(any(OrdenDTO.class))).thenReturn(creada);

        mockMvc.perform(post("/api/v2/ordenes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Orden creada"))
                .andExpect(jsonPath("$.data.numeroOrden").value(1))
                .andExpect(jsonPath("$.data.fechaCreacion").value("2026-03-20"))
                .andExpect(jsonPath("$.data.fechaRecibida").value("2026-03-23"))
                .andExpect(jsonPath("$.data.total").value(115000))
                .andExpect(jsonPath("$.data.descuentoAplicado").value(0));
    }

    @Test
    void debeActualizarOrden() throws Exception {
        OrdenDTO dto = new OrdenDTO();
        dto.setNumeroOrden(1);
        dto.setFechaCreacion("2026-03-20");
        dto.setFechaRecibida("2026-03-23");
        dto.setTotal(115000);
        dto.setDescuentoAplicado(0);

        Orden actualizada = new Orden(1L, 1, "2026-03-20", "2026-03-23", 115000, 0);

        when(service.actualizar(eq(1L), any(OrdenDTO.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v2/ordenes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Orden actualizada"))
                .andExpect(jsonPath("$.data.numeroOrden").value(1))
                .andExpect(jsonPath("$.data.fechaCreacion").value("2026-03-20"))
                .andExpect(jsonPath("$.data.fechaRecibida").value("2026-03-23"))
                .andExpect(jsonPath("$.data.total").value(115000))
                .andExpect(jsonPath("$.data.descuentoAplicado").value(0));
    }

    @Test
    void debeEliminarOrden() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/v2/ordenes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Orden eliminada"));
    }
}

