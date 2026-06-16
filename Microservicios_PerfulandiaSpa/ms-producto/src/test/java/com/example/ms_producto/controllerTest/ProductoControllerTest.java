package com.example.ms_producto.controllerTest;

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

import com.example.ms_producto.controller.ProductoController;
import com.example.ms_producto.dto.CategoriaResponse;
import com.example.ms_producto.dto.ProductoDTO;
import com.example.ms_producto.dto.ProductoResponse;
import com.example.ms_producto.security.JwtUtil;
import com.example.ms_producto.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductoController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductoService productoService;

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
    void debeListarProductos() throws Exception {

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        ProductoResponse producto = ProductoResponse.builder()
                .id(1L)
                .nombre("Dior Sauvage")
                .descripcion("Fragancia masculina 100ml")
                .precio(130000.0)
                .cantidad(40)
                .categoria(categoria)
                .build();

        when(productoService.listar(anyString())).thenReturn(List.of(producto));

        mockMvc.perform(get("/api/v2/productos")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].nombre").value("Dior Sauvage"))
                .andExpect(jsonPath("$.data[0].precio").value(130000.0));
    }

    @Test
    void debeObtenerProductoPorId() throws Exception {

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        ProductoResponse producto = ProductoResponse.builder()
                .id(1L)
                .nombre("Dior Sauvage")
                .descripcion("Fragancia masculina 100ml")
                .precio(130000.0)
                .cantidad(40)
                .categoria(categoria)
                .build();

        when(productoService.obtener(eq(1L), anyString())).thenReturn(producto);

        mockMvc.perform(get("/api/v2/productos/1")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Producto obtenido"))
                .andExpect(jsonPath("$.data.nombre").value("Dior Sauvage"))
                .andExpect(jsonPath("$.data.precio").value(130000.0));
    }

    @Test
    void debeCrearProducto() throws Exception {

        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Dior Sauvage");
        dto.setDescripcion("Fragancia masculina 100ml");
        dto.setPrecio(130000.0);
        dto.setCantidad(40);
        dto.setCategoria(1L);

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        ProductoResponse creado = ProductoResponse.builder()
                .id(1L)
                .nombre("Dior Sauvage")
                .descripcion("Fragancia masculina 100ml")
                .precio(130000.0)
                .cantidad(40)
                .categoria(categoria)
                .build();

        when(productoService.crear(any(ProductoDTO.class), anyString())).thenReturn(creado);

        mockMvc.perform(post("/api/v2/productos")
                        .header("Authorization", "Bearer test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Producto Creado"))
                .andExpect(jsonPath("$.data.nombre").value("Dior Sauvage"))
                .andExpect(jsonPath("$.data.precio").value(130000.0));
    }

    @Test
    void debeActualizarProducto() throws Exception {

        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Producto Actualizado");
        dto.setDescripcion("Desc actualizada");
        dto.setPrecio(150000.0);
        dto.setCantidad(20);
        dto.setCategoria(1L);

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        ProductoResponse actualizado = ProductoResponse.builder()
                .id(1L)
                .nombre("Producto Actualizado")
                .descripcion("Desc actualizada")
                .precio(150000.0)
                .cantidad(20)
                .categoria(categoria)
                .build();

        when(productoService.actualizar(eq(1L), any(ProductoDTO.class), anyString())).thenReturn(actualizado);

        mockMvc.perform(put("/api/v2/productos/1")
                        .header("Authorization", "Bearer test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Producto actualizado"))
                .andExpect(jsonPath("$.data.nombre").value("Producto Actualizado"))
                .andExpect(jsonPath("$.data.precio").value(150000.0));
    }

    @Test
    void debeEliminarProducto() throws Exception {

        doNothing().when(productoService).eliminar(1L);

        mockMvc.perform(delete("/api/v2/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Producto eliminado"));
    }
}