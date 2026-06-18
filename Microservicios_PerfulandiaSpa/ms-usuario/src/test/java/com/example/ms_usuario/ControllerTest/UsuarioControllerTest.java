package com.example.ms_usuario.ControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.example.ms_usuario.controller.UsuarioController;
import com.example.ms_usuario.dto.UsuarioDTO;
import com.example.ms_usuario.model.Usuario;
import com.example.ms_usuario.security.JwtUtil;
import com.example.ms_usuario.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService UsService;

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
    void debeListarUsuarios() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setUsername("juanp");
        usuario.setEmail("juan@perfulandia.cl");
        usuario.setDireccion("Santiago Centro 101");
        usuario.setTelefono("911111111");
        usuario.setTipo("USER");
        usuario.setPassword("1234");

        when(UsService.listar()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/v2/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.data[0].username").value("juanp"));
    }

    @Test
    void debeObtenerUsuarioPorId() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setUsername("juanp");
        usuario.setEmail("juan@perfulandia.cl");
        usuario.setDireccion("Santiago Centro 101");
        usuario.setTelefono("911111111");
        usuario.setTipo("USER");
        usuario.setPassword("1234");

        when(UsService.obtener(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/v2/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario obtenido"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.email").value("juan@perfulandia.cl"));
    }

    @Test
    void debeCrearUsuario() throws Exception {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Juan Pérez");
        dto.setUsername("juanp");
        dto.setEmail("juan@perfulandia.cl");
        dto.setDireccion("Santiago Centro 101");
        dto.setTelefono("911111111");
        dto.setTipo("USER");
        dto.setPassword("1234");

        Usuario creado = new Usuario();
        creado.setId(1L);
        creado.setNombre("Juan Pérez");
        creado.setUsername("juanp");
        creado.setEmail("juan@perfulandia.cl");
        creado.setDireccion("Santiago Centro 101");
        creado.setTelefono("911111111");
        creado.setTipo("USER");
        creado.setPassword("1234");

        when(UsService.crear(any(UsuarioDTO.class))).thenReturn(creado);

        mockMvc.perform(post("/api/v2/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario creado"))
                .andExpect(jsonPath("$.data.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.data.username").value("juanp"));
    }

    @Test
    void debeRetornar400SiDatosInvalidos() throws Exception {
        UsuarioDTO dtoInvalido = new UsuarioDTO();

        mockMvc.perform(post("/api/v2/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void debeActualizarUsuario() throws Exception {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Nombre Actualizado");
        dto.setUsername("juanp");
        dto.setEmail("juan@perfulandia.cl");
        dto.setDireccion("Santiago Centro 101");
        dto.setTelefono("911111111");
        dto.setTipo("ADMIN");
        dto.setPassword("1234");

        Usuario actualizado = new Usuario();
        actualizado.setId(1L);
        actualizado.setNombre("Nombre Actualizado");
        actualizado.setUsername("juanp");
        actualizado.setEmail("juan@perfulandia.cl");
        actualizado.setDireccion("Santiago Centro 101");
        actualizado.setTelefono("911111111");
        actualizado.setTipo("ADMIN");
        actualizado.setPassword("1234");

        when(UsService.actualizar(eq(1L), any(UsuarioDTO.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v2/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario actualizado"))
                .andExpect(jsonPath("$.data.nombre").value("Nombre Actualizado"))
                .andExpect(jsonPath("$.data.tipo").value("ADMIN"));
    }

    @Test
    void debeEliminarUsuario() throws Exception {
        doNothing().when(UsService).eliminar(1L);

        mockMvc.perform(delete("/api/v2/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario eliminado"));
    }
}
