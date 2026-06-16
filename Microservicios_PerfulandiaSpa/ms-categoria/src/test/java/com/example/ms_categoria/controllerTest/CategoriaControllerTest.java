package com.example.ms_categoria.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ms_categoria.controller.CategoriaController;
import com.example.ms_categoria.dto.CategoriaDTO;
import com.example.ms_categoria.model.Categoria;
import com.example.ms_categoria.security.JwtUtil;
import com.example.ms_categoria.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CategoriaController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoriaService service;

    @MockitoBean
    private JwtUtil jwtUtil;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Test
    void debeListarCategorias() throws Exception {
        List<Categoria> categorias = List.of(
                new Categoria(1L, "Perfume Hombre", "Fragancias masculinas")
        );

        when(service.listar()).thenReturn(categorias);

        mockMvc.perform(get("/api/v2/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].nombre").value("Perfume Hombre"))
                .andExpect(jsonPath("$.data[0].descripcion").value("Fragancias masculinas"));
    }

    @Test
    void debeObtenerCategoriaPorId() throws Exception {
        Categoria categoria = new Categoria(1L, "Perfume Hombre", "Fragancias masculinas");

        when(service.obtener(1L)).thenReturn(categoria);

        mockMvc.perform(get("/api/v2/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Categoría obtenida"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nombre").value("Perfume Hombre"))
                .andExpect(jsonPath("$.data.descripcion").value("Fragancias masculinas"));
    }

    @Test
    void debeCrearCategoria() throws Exception {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Perfume Mujer");
        dto.setDescripcion("Fragancias femeninas");

        Categoria creada = new Categoria(1L, "Perfume Mujer", "Fragancias femeninas");

        when(service.crear(any(CategoriaDTO.class))).thenReturn(creada);

        mockMvc.perform(post("/api/v2/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Categoría creada"))
                .andExpect(jsonPath("$.data.nombre").value("Perfume Mujer"))
                .andExpect(jsonPath("$.data.descripcion").value("Fragancias femeninas"));
    }

    @Test
    void debeActualizarCategoria() throws Exception {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Categoria Actualizada");
        dto.setDescripcion("Descripcion actualizada");

        Categoria actualizada = new Categoria(1L, "Categoria Actualizada", "Descripcion actualizada");

        when(service.actualizar(eq(1L), any(CategoriaDTO.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v2/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Categoría actualizada"))
                .andExpect(jsonPath("$.data.nombre").value("Categoria Actualizada"))
                .andExpect(jsonPath("$.data.descripcion").value("Descripcion actualizada"));
    }

    @Test
    void debeEliminarCategoria() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/v2/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Categoría eliminada"));
    }
}