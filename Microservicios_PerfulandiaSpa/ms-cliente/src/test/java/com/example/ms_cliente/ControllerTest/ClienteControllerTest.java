package com.example.ms_cliente.ControllerTest;

import com.example.ms_cliente.controller.ClienteController;
import com.example.ms_cliente.dto.ClienteDTO;
import com.example.ms_cliente.dto.ClienteResponse;
import com.example.ms_cliente.dto.UsuarioResponse;
import com.example.ms_cliente.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
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

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService clienteService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    private static final String TOKEN = "Bearer test-token";

    @Test
    void debeListarClientes() throws Exception {
        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan@perfulandia.cl");

        ClienteResponse cliente = ClienteResponse.builder()
                .id(1L)
                .fechaRegistro("2026-01-10")
                .direccionEnvio("Av. Principal 456")
                .usuario(usuario)
                .build();

        when(clienteService.listar(TOKEN)).thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/clientes")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].fechaRegistro").value("2026-01-10"))
                .andExpect(jsonPath("$.data[0].usuario.nombre").value("Juan Pérez"));
    }

    @Test
    void debeObtenerClientePorId() throws Exception {
        ClienteResponse cliente = ClienteResponse.builder()
                .id(1L)
                .fechaRegistro("2026-01-10")
                .direccionEnvio("Av. Principal 456")
                .build();

        when(clienteService.obtener(1L, TOKEN)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.fechaRegistro").value("2026-01-10"))
                .andExpect(jsonPath("$.data.direccionEnvio").value("Av. Principal 456"));
    }

    @Test
    void debeCrearCliente() throws Exception {
        ClienteDTO dto = new ClienteDTO();
        dto.setFechaRegistro("2026-01-10");
        dto.setDireccionEnvio("Av. Principal 456");
        dto.setUsuarioId(1L);

        ClienteResponse creado = ClienteResponse.builder()
                .id(1L)
                .fechaRegistro("2026-01-10")
                .direccionEnvio("Av. Principal 456")
                .build();

        when(clienteService.crear(any(ClienteDTO.class), eq(TOKEN))).thenReturn(creado);

        mockMvc.perform(post("/api/clientes")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cliente creado"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.direccionEnvio").value("Av. Principal 456"));
    }

    @Test
    void debeRetornar400SiDatosInvalidos() throws Exception {
        ClienteDTO dtoInvalido = new ClienteDTO();

        mockMvc.perform(post("/api/clientes")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void debeActualizarCliente() throws Exception {
        ClienteDTO dto = new ClienteDTO();
        dto.setFechaRegistro("2026-03-01");
        dto.setDireccionEnvio("Las Condes 303");
        dto.setUsuarioId(1L);

        ClienteResponse actualizado = ClienteResponse.builder()
                .id(1L)
                .fechaRegistro("2026-03-01")
                .direccionEnvio("Las Condes 303")
                .build();

        when(clienteService.actualizar(eq(1L), any(ClienteDTO.class), eq(TOKEN)))
                .thenReturn(actualizado);

        mockMvc.perform(put("/api/clientes/1")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cliente actualizado"))
                .andExpect(jsonPath("$.data.direccionEnvio").value("Las Condes 303"));
    }

    @Test
    void debeEliminarCliente() throws Exception {
        doNothing().when(clienteService).eliminar(1L);

        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cliente eliminado"));
    }
}
