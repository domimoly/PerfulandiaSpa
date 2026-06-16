package com.example.ms_devolucion.controller;

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

import com.example.ms_devolucion.dto.DevolucionDTO;
import com.example.ms_devolucion.model.Devolucion;
import com.example.ms_devolucion.service.DevolucionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DevolucionController.class)
@AutoConfigureMockMvc(addFilters = false)
class DevolucionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DevolucionService service;

    @Test
    void debeListarDevoluciones() throws Exception {
        List<Devolucion> devoluciones = List.of(
                new Devolucion(1L, "2026-05-09", "Producto defectuoso", "Pendiente")
        );

        when(service.listar()).thenReturn(devoluciones);

        mockMvc.perform(get("/api/v2/devoluciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].fechaDevolucion").value("2026-05-09"))
                .andExpect(jsonPath("$.data[0].motivo").value("Producto defectuoso"))
                .andExpect(jsonPath("$.data[0].estado").value("Pendiente"));
    }

    @Test
    void debeObtenerDevolucionPorId() throws Exception {
        Devolucion devolucion = new Devolucion(1L, "2026-05-09", "Producto defectuoso", "Pendiente");

        when(service.obtener(1L)).thenReturn(devolucion);

        mockMvc.perform(get("/api/v2/devoluciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Devolucion obtenida"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.fechaDevolucion").value("2026-05-09"))
                .andExpect(jsonPath("$.data.motivo").value("Producto defectuoso"))
                .andExpect(jsonPath("$.data.estado").value("Pendiente"));
    }

    @Test
    void debeCrearDevolucion() throws Exception {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setFechaDevolucion("2026-05-09");
        dto.setMotivo("Producto defectuoso");
        dto.setEstado("Pendiente"   );

        Devolucion creada = new Devolucion(1L, "2026-05-09", "Producto defectuoso", "Pendiente");

        when(service.crear(any(DevolucionDTO.class))).thenReturn(creada);

        mockMvc.perform(post("/api/v2/devoluciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Devolucion creada"))
                .andExpect(jsonPath("$.data.fechaDevolucion").value("2026-05-09"))
                .andExpect(jsonPath("$.data.motivo").value("Producto defectuoso"))
                .andExpect(jsonPath("$.data.estado").value("Pendiente"));
    }

    @Test
    void debeActualizarDevolucion() throws Exception {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setFechaDevolucion("2026-05-09");
        dto.setMotivo("Producto defectuoso");
        dto.setEstado("Aprobada");

        Devolucion actualizada = new Devolucion(1L, "2026-05-09", "Producto defectuoso", "Aprobada");

        when(service.actualizar(eq(1L), any(DevolucionDTO.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v2/devoluciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Devolucion actualizada"))
                .andExpect(jsonPath("$.data.fechaDevolucion").value("2026-05-09"))
                .andExpect(jsonPath("$.data.motivo").value("Producto defectuoso"))
                .andExpect(jsonPath("$.data.estado").value("Aprobada"));
    }

    @Test
    void debeEliminarDevolucion() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/v2/devoluciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Devolucion eliminada"));
    }
}
