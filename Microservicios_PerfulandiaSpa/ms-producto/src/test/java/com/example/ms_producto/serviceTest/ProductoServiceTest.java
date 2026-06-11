package com.example.ms_producto.serviceTest;

import com.example.ms_producto.client.CategoriaClient;
import com.example.ms_producto.dto.CategoriaResponse;
import com.example.ms_producto.dto.ProductoDTO;
import com.example.ms_producto.dto.ProductoResponse;
import com.example.ms_producto.model.Producto;
import com.example.ms_producto.repository.ProductoRepository;
import com.example.ms_producto.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepo;

    @Mock
    private CategoriaClient categoriaClient;

    @InjectMocks
    private ProductoService service;

    private final String token = "Bearer test";

    @Test
    void deberiaRetornarProductoCuandoExiste() {

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Dior Sauvage");
        producto.setDescripcion("Fragancia masculina 100ml");
        producto.setPrecio(130000.0);
        producto.setCantidad(40);
        producto.setCategoria(1L);

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        when(productoRepo.findById(1L)).thenReturn(Optional.of(producto));
        when(categoriaClient.obtenerCategoria(anyLong(), anyString())).thenReturn(categoria);

        ProductoResponse resultado = service.obtener(1L, token);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Dior Sauvage", resultado.getNombre());

        verify(productoRepo).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoProductoNoExiste() {

        when(productoRepo.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> service.obtener(99L, token)
        );

        assertEquals("Producto no encontrado", ex.getMessage());

        verify(productoRepo).findById(99L);
    }

    @Test
    void deberiaRetornarListaProductos() {

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Dior Sauvage");
        producto.setDescripcion("Fragancia masculina 100ml");
        producto.setPrecio(130000.0);
        producto.setCantidad(40);
        producto.setCategoria(1L);

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        when(productoRepo.findAll()).thenReturn(List.of(producto));
        when(categoriaClient.obtenerCategoria(anyLong(), anyString())).thenReturn(categoria);

        List<ProductoResponse> resultado = service.listar(token);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Dior Sauvage", resultado.get(0).getNombre());

        verify(productoRepo).findAll();
    }

    @Test
    void deberiaCrearProductoCorrectamente() {

        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Dior Sauvage");
        dto.setDescripcion("Fragancia masculina 100ml");
        dto.setPrecio(130000.0);
        dto.setCantidad(40);
        dto.setCategoria(1L);

        Producto productoGuardado = new Producto();
        productoGuardado.setId(1L);
        productoGuardado.setNombre(dto.getNombre());
        productoGuardado.setDescripcion(dto.getDescripcion());
        productoGuardado.setPrecio(dto.getPrecio());
        productoGuardado.setCantidad(dto.getCantidad());
        productoGuardado.setCategoria(dto.getCategoria());

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        when(categoriaClient.obtenerCategoria(anyLong(), anyString())).thenReturn(categoria);
        when(productoRepo.save(any(Producto.class))).thenReturn(productoGuardado);

        ProductoResponse resultado = service.crear(dto, token);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Dior Sauvage", resultado.getNombre());

        verify(productoRepo).save(any(Producto.class));
    }

    @Test
    void deberiaActualizarProductoCorrectamente() {

        Producto existente = new Producto();
        existente.setId(1L);
        existente.setNombre("Producto viejo");
        existente.setDescripcion("Desc vieja");
        existente.setPrecio(10000.0);
        existente.setCantidad(5);
        existente.setCategoria(1L);

        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Producto nuevo");
        dto.setDescripcion("Desc nueva");
        dto.setPrecio(20000.0);
        dto.setCantidad(10);
        dto.setCategoria(1L);

        CategoriaResponse categoria = new CategoriaResponse();
        categoria.setId(1L);
        categoria.setNombre("Perfume Hombre");
        categoria.setDescripcion("Fragancias masculinas");

        when(categoriaClient.obtenerCategoria(anyLong(), anyString())).thenReturn(categoria);
        when(productoRepo.findById(1L)).thenReturn(Optional.of(existente));
        when(productoRepo.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductoResponse resultado = service.actualizar(1L, dto, token);

        assertEquals("Producto nuevo", resultado.getNombre());
        assertEquals(20000.0, resultado.getPrecio());

        verify(productoRepo).findById(1L);
        verify(productoRepo).save(existente);
    }

    @Test
    void deberiaEliminarProductoPorId() {

        doNothing().when(productoRepo).deleteById(1L);

        service.eliminar(1L);

        verify(productoRepo).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionSiCategoriaNoExisteAlCrear() {

        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Producto sin categoria");
        dto.setDescripcion("Desc test");
        dto.setPrecio(10000.0);
        dto.setCantidad(5);
        dto.setCategoria(99L);

        when(categoriaClient.obtenerCategoria(anyLong(), anyString())).thenReturn(null);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.crear(dto, token)
        );

        assertEquals("Categoría no existe", ex.getMessage());

        verify(productoRepo, never()).save(any());
    }
}