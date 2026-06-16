package com.example.ms_cupon_descuento.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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

import com.example.ms_cupon_descuento.controller.CuponDescuentoController;
import com.example.ms_cupon_descuento.dto.CategoriaResponse;
import com.example.ms_cupon_descuento.dto.CuponDescuentoDTO;
import com.example.ms_cupon_descuento.dto.CuponResponse;
import com.example.ms_cupon_descuento.security.JwtUtil;
import com.example.ms_cupon_descuento.service.CuponDescuentoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CuponDescuentoController.class)
@AutoConfigureMockMvc(addFilters = false)
class CuponDescuentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CuponDescuentoService cuponService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper().findAndRegisterModules();
        }
    }

    @Test
    void debeListarCupones() throws Exception {

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        CuponResponse cupon = CuponResponse.builder()
                .id(1L)
                .categoria(categoria)
                .codigo("HOMBRE20")
                .porcentajeDescuento(20.0)
                .fechaVencimiento(LocalDate.of(2026, 12, 31))
                .activo(true)
                .build();

        when(cuponService.listar(anyString())).thenReturn(List.of(cupon));

        mockMvc.perform(get("/api/v2/cupones")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].codigo").value("HOMBRE20"))
                .andExpect(jsonPath("$.data[0].porcentajeDescuento").value(20.0));
    }

    @Test
    void debeObtenerCuponPorId() throws Exception {

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        CuponResponse cupon = CuponResponse.builder()
                .id(1L)
                .categoria(categoria)
                .codigo("HOMBRE20")
                .porcentajeDescuento(20.0)
                .fechaVencimiento(LocalDate.of(2026, 12, 31))
                .activo(true)
                .build();

        when(cuponService.obtener(eq(1L), anyString())).thenReturn(cupon);

        mockMvc.perform(get("/api/v2/cupones/1")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cupón obtenido"))
                .andExpect(jsonPath("$.data.codigo").value("HOMBRE20"))
                .andExpect(jsonPath("$.data.porcentajeDescuento").value(20.0));
    }

    @Test
    void debeCrearCupon() throws Exception {

        CuponDescuentoDTO dto = new CuponDescuentoDTO();
        dto.setCategoria(1L);
        dto.setCodigo("HOMBRE20");
        dto.setPorcentajeDescuento(20.0);
        dto.setFechaVencimiento(LocalDate.of(2026, 12, 31));
        dto.setActivo(true);

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        CuponResponse creado = CuponResponse.builder()
                .id(1L)
                .categoria(categoria)
                .codigo("HOMBRE20")
                .porcentajeDescuento(20.0)
                .fechaVencimiento(LocalDate.of(2026, 12, 31))
                .activo(true)
                .build();

        when(cuponService.crear(any(CuponDescuentoDTO.class), anyString())).thenReturn(creado);

        mockMvc.perform(post("/api/v2/cupones")
                        .header("Authorization", "Bearer test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cupón Creado"))
                .andExpect(jsonPath("$.data.codigo").value("HOMBRE20"))
                .andExpect(jsonPath("$.data.porcentajeDescuento").value(20.0));
    }

    @Test
    void debeActualizarCupon() throws Exception {

        CuponDescuentoDTO dto = new CuponDescuentoDTO();
        dto.setCategoria(1L);
        dto.setCodigo("NUEVO30");
        dto.setPorcentajeDescuento(30.0);
        dto.setFechaVencimiento(LocalDate.of(2026, 12, 31));
        dto.setActivo(true);

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        CuponResponse actualizado = CuponResponse.builder()
                .id(1L)
                .categoria(categoria)
                .codigo("NUEVO30")
                .porcentajeDescuento(30.0)
                .fechaVencimiento(LocalDate.of(2026, 12, 31))
                .activo(true)
                .build();

        when(cuponService.actualizar(eq(1L), any(CuponDescuentoDTO.class), anyString())).thenReturn(actualizado);

        mockMvc.perform(put("/api/v2/cupones/1")
                        .header("Authorization", "Bearer test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cupón actualizado"))
                .andExpect(jsonPath("$.data.codigo").value("NUEVO30"))
                .andExpect(jsonPath("$.data.porcentajeDescuento").value(30.0));
    }

    @Test
    void debeEliminarCupon() throws Exception {

        doNothing().when(cuponService).eliminar(1L);

        mockMvc.perform(delete("/api/v2/cupones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cupón eliminado"));
    }
}