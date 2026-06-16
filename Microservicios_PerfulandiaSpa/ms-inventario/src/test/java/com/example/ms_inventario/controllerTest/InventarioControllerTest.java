package com.example.ms_inventario.controllerTest;

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

import com.example.ms_inventario.controller.InventarioController;
import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.dto.InventarioResponse;
import com.example.ms_inventario.dto.ProductoResponse;
import com.example.ms_inventario.dto.ProveedorResponse;
import com.example.ms_inventario.dto.SucursalResponse;
import com.example.ms_inventario.security.JwtUtil;
import com.example.ms_inventario.service.InventarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(InventarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InventarioService invService;

    @MockitoBean
    private JwtUtil jwtUtil;

    /*Aclaración | Se tomó la retroalimentación hecha en clases y se modificó de tal forma para que el LocalDate
                   pase como String y no se tenga que agregar como fecha t_t
    */
    @TestConfiguration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper()
                    .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .findAndRegisterModules();
        }
    }

    @Test
    void debeListarInventarios() throws Exception {

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProveedorResponse proveedor = new ProveedorResponse();
        proveedor.setNombre("Perfumeria Italiana");

        InventarioResponse inv = InventarioResponse.builder()
                .id(1L)
                .producto(producto)
                .sucursal(sucursal)
                .cantidad(50)
                .stockMinimo(10)
                .fechaInventario(LocalDate.of(2025, 1, 15))
                .proveedor(proveedor)
                .build();

        when(invService.listar(anyString())).thenReturn(List.of(inv));

        mockMvc.perform(get("/api/v2/inventarios")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].cantidad").value(50));
    }

    @Test
    void debeObtenerInventarioPorId() throws Exception {

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProveedorResponse proveedor = new ProveedorResponse();
        proveedor.setNombre("Perfumeria Italiana");

        InventarioResponse inv = InventarioResponse.builder()
                .id(1L)
                .producto(producto)
                .sucursal(sucursal)
                .cantidad(50)
                .stockMinimo(10)
                .fechaInventario(LocalDate.of(2025, 1, 15))
                .proveedor(proveedor)
                .build();

        when(invService.obtener(eq(1L), anyString())).thenReturn(inv);

        mockMvc.perform(get("/api/v2/inventarios/1")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Inventario obtenido"))
                .andExpect(jsonPath("$.data.cantidad").value(50));
    }

    @Test
    void debeCrearInventario() throws Exception {

        InventarioDTO dto = new InventarioDTO();
        dto.setProducto(1L);
        dto.setSucursal(1L);
        dto.setCantidad(50);
        dto.setStockMinimo(10);
        dto.setFechaInventario(LocalDate.of(2025, 1, 15));
        dto.setProveedor(1L);

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProveedorResponse proveedor = new ProveedorResponse();
        proveedor.setNombre("Perfumeria Italiana");

        InventarioResponse creado = InventarioResponse.builder()
                .id(1L)
                .producto(producto)
                .sucursal(sucursal)
                .cantidad(50)
                .stockMinimo(10)
                .fechaInventario(LocalDate.of(2025, 1, 15))
                .proveedor(proveedor)
                .build();

        when(invService.crear(any(InventarioDTO.class), anyString())).thenReturn(creado);

        mockMvc.perform(post("/api/v2/inventarios")
                        .header("Authorization", "Bearer test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Inventario Creado"))
                .andExpect(jsonPath("$.data.cantidad").value(50));
    }

    @Test
    void debeActualizarInventario() throws Exception {

        InventarioDTO dto = new InventarioDTO();
        dto.setProducto(1L);
        dto.setSucursal(1L);
        dto.setCantidad(100);
        dto.setStockMinimo(15);
        dto.setFechaInventario(LocalDate.of(2025, 6, 1));
        dto.setProveedor(1L);

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProveedorResponse proveedor = new ProveedorResponse();
        proveedor.setNombre("Perfumeria Italiana");

        InventarioResponse actualizado = InventarioResponse.builder()
                .id(1L)
                .producto(producto)
                .sucursal(sucursal)
                .cantidad(100)
                .stockMinimo(15)
                .fechaInventario(LocalDate.of(2025, 6, 1))
                .proveedor(proveedor)
                .build();

        when(invService.actualizar(eq(1L), any(InventarioDTO.class), anyString())).thenReturn(actualizado);

        mockMvc.perform(put("/api/v2/inventarios/1")
                        .header("Authorization", "Bearer test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Inventario actualizado"))
                .andExpect(jsonPath("$.data.cantidad").value(100));
    }

    @Test
    void debeEliminarInventario() throws Exception {

        doNothing().when(invService).eliminar(1L);

        mockMvc.perform(delete("/api/v2/inventarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Inventario eliminado"));
    }
}