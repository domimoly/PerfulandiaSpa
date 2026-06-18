package com.example.ms_resena.ControllerTest;

import com.example.ms_resena.controller.ResenaController; 
import com.example.ms_resena.dto.ResenaDTO;
import com.example.ms_resena.dto.ResenaResponse;
import com.example.ms_resena.dto.UsuarioResponse;
import com.example.ms_resena.security.JwtUtil;
import com.example.ms_resena.service.ResenaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResenaController.class)
@AutoConfigureMockMvc(addFilters = false)
class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ResenaService resenaService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    private static final String TOKEN = "Bearer test-token";


    @Test
    void debeListarResenas() throws Exception {
        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");

        ResenaResponse resena = ResenaResponse.builder()
                .id(1L)
                .puntuacion(5)
                .comentario("Excelente perfume, muy duradero")
                .fechaResena("2026-01-15")
                .usuario(usuario)
                .build();

        when(resenaService.listar(TOKEN)).thenReturn(List.of(resena));

        mockMvc.perform(get("/api/v2/resenas")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].puntuacion").value(5))
                .andExpect(jsonPath("$.data[0].usuario.nombre").value("Juan Pérez"));
        }

    @Test
    void debeObtenerResenaPorId() throws Exception {
        ResenaResponse resena = ResenaResponse.builder()
                .id(1L)
                .puntuacion(5)
                .comentario("Excelente perfume, muy duradero")
                .fechaResena("2026-01-15")
                .build();

        when(resenaService.obtener(1L, TOKEN)).thenReturn(resena);

        mockMvc.perform(get("/api/v2/resenas/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.puntuacion").value(5))
                .andExpect(jsonPath("$.data.comentario").value("Excelente perfume, muy duradero"));
    }

    @Test
    void debeCrearResena() throws Exception {
        ResenaDTO dto = new ResenaDTO();
        dto.setPuntuacion(5);
        dto.setComentario("Excelente perfume, muy duradero");
        dto.setFechaResena("2026-01-15");
        dto.setUsuarioId(1L);


        ResenaResponse creada = ResenaResponse.builder()
                .id(1L)
                .puntuacion(5)
                .comentario("Excelente perfume, muy duradero")
                .fechaResena("2026-01-15")
                .build();

        when(resenaService.crear(any(ResenaDTO.class), eq(TOKEN))).thenReturn(creada);

        mockMvc.perform(post("/api/v2/resenas")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Reseña creada"))
                .andExpect(jsonPath("$.data.puntuacion").value(5))
                .andExpect(jsonPath("$.data.comentario").value("Excelente perfume, muy duradero"));
    }

    @Test
    void debeRetornar400SiDatosInvalidos() throws Exception {
        ResenaDTO dtoInvalido = new ResenaDTO();

        mockMvc.perform(post("/api/v2/resenas")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void debeActualizarResena() throws Exception {
        ResenaDTO dto = new ResenaDTO();
        dto.setPuntuacion(4);
        dto.setComentario("Muy bueno, lo recomiendo");
        dto.setFechaResena("2026-02-01");
        dto.setUsuarioId(1L);
        
        ResenaResponse actualizada = ResenaResponse.builder()
                .id(1L)
                .puntuacion(4)
                .comentario("Muy bueno, lo recomiendo")
                .fechaResena("2026-02-01")
                .build();

        when(resenaService.actualizar(eq(1L), any(ResenaDTO.class), eq(TOKEN)))
                .thenReturn(actualizada);

        mockMvc.perform(put("/api/v2/resenas/1")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Reseña actualizada"))
                .andExpect(jsonPath("$.data.puntuacion").value(4))
                .andExpect(jsonPath("$.data.comentario").value("Muy bueno, lo recomiendo"));
    }

    @Test
    void debeEliminarResena() throws Exception {
        doNothing().when(resenaService).eliminar(1L);

        mockMvc.perform(delete("/api/v2/resenas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Reseña eliminada"));
    }
}
