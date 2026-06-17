package com.example.ms_proveedor.controllerTest;

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

import com.example.ms_proveedor.controller.ProveedorController;
import com.example.ms_proveedor.dto.ProductoResponse;
import com.example.ms_proveedor.dto.ProveedorDTO;
import com.example.ms_proveedor.dto.ProveedorResponse;
import com.example.ms_proveedor.dto.SucursalResponse;
import com.example.ms_proveedor.security.JwtUtil;
import com.example.ms_proveedor.service.ProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProveedorController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProveedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProveedorService proveedorService;

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
    void debeListarProveedores() throws Exception {

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        ProveedorResponse proveedor = ProveedorResponse.builder()
                .id(1L)
                .nombre("Perfumeria Italiana")
                .email("contacto@perfumeriaitaliana.cl")
                .telefono("+56975842043")
                .direccion("Av. Italia 1439")
                .sucursal(sucursal)
                .producto(producto)
                .build();

        when(proveedorService.listar(anyString())).thenReturn(List.of(proveedor));

        mockMvc.perform(get("/api/v2/proveedores")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado Obtenido"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].nombre").value("Perfumeria Italiana"))
                .andExpect(jsonPath("$.data[0].email").value("contacto@perfumeriaitaliana.cl"));
    }

    @Test
    void debeObtenerProveedorPorId() throws Exception {

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        ProveedorResponse proveedor = ProveedorResponse.builder()
                .id(1L)
                .nombre("Perfumeria Italiana")
                .email("contacto@perfumeriaitaliana.cl")
                .telefono("+56975842043")
                .direccion("Av. Italia 1439")
                .sucursal(sucursal)
                .producto(producto)
                .build();

        when(proveedorService.obtener(eq(1L), anyString())).thenReturn(proveedor);

        mockMvc.perform(get("/api/v2/proveedores/1")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Proveedor Obtenido"))
                .andExpect(jsonPath("$.data.nombre").value("Perfumeria Italiana"))
                .andExpect(jsonPath("$.data.email").value("contacto@perfumeriaitaliana.cl"));
    }

    @Test
    void debeCrearProveedor() throws Exception {

        ProveedorDTO dto = new ProveedorDTO();
        dto.setNombre("Perfumeria Italiana");
        dto.setEmail("contacto@perfumeriaitaliana.cl");
        dto.setTelefono("+56975842043");
        dto.setDireccion("Av. Italia 1439");
        dto.setSucursal(1L);
        dto.setProducto(1L);

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        ProveedorResponse creado = ProveedorResponse.builder()
                .id(1L)
                .nombre("Perfumeria Italiana")
                .email("contacto@perfumeriaitaliana.cl")
                .telefono("+56975842043")
                .direccion("Av. Italia 1439")
                .sucursal(sucursal)
                .producto(producto)
                .build();

        when(proveedorService.crear(any(ProveedorDTO.class), anyString())).thenReturn(creado);

        mockMvc.perform(post("/api/v2/proveedores")
                        .header("Authorization", "Bearer test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Proveedor Creado"))
                .andExpect(jsonPath("$.data.nombre").value("Perfumeria Italiana"))
                .andExpect(jsonPath("$.data.email").value("contacto@perfumeriaitaliana.cl"));
    }

    @Test
    void debeActualizarProveedor() throws Exception {

        ProveedorDTO dto = new ProveedorDTO();
        dto.setNombre("Proveedor Actualizado");
        dto.setEmail("nuevo@test.cl");
        dto.setTelefono("999999999");
        dto.setDireccion("Direccion nueva");
        dto.setSucursal(1L);
        dto.setProducto(1L);

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        ProveedorResponse actualizado = ProveedorResponse.builder()
                .id(1L)
                .nombre("Proveedor Actualizado")
                .email("nuevo@test.cl")
                .telefono("999999999")
                .direccion("Direccion nueva")
                .sucursal(sucursal)
                .producto(producto)
                .build();

        when(proveedorService.actualizar(eq(1L), any(ProveedorDTO.class), anyString())).thenReturn(actualizado);

        mockMvc.perform(put("/api/v2/proveedores/1")
                        .header("Authorization", "Bearer test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Proveedor Actualizado"))
                .andExpect(jsonPath("$.data.nombre").value("Proveedor Actualizado"))
                .andExpect(jsonPath("$.data.email").value("nuevo@test.cl"));
    }

    @Test
    void debeEliminarProveedor() throws Exception {

        doNothing().when(proveedorService).eliminar(1L);

        mockMvc.perform(delete("/api/v2/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Proveedor Eliminado"));
    }
}