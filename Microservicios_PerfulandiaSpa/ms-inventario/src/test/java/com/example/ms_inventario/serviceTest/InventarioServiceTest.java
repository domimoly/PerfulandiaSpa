package com.example.ms_inventario.serviceTest;

import com.example.ms_inventario.client.ProductoClient;
import com.example.ms_inventario.client.ProveedorClient;
import com.example.ms_inventario.client.SucursalClient;
import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.dto.InventarioResponse;
import com.example.ms_inventario.dto.ProductoResponse;
import com.example.ms_inventario.dto.ProveedorResponse;
import com.example.ms_inventario.dto.SucursalResponse;
import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.repository.InventarioRepository;
import com.example.ms_inventario.service.InventarioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository invRepo;

    @Mock
    private ProductoClient productoClient;

    @Mock
    private SucursalClient sucursalClient;

    @Mock
    private ProveedorClient proveedorClient;

    @InjectMocks
    private InventarioService service;

    private final String token = "Bearer test";

    @Test
    void deberiaRetornarInventarioCuandoExiste() {

        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProducto(1L);
        inventario.setSucursal(1L);
        inventario.setCantidad(50);
        inventario.setStockMinimo(10);
        inventario.setFechaInventario(LocalDate.of(2025, 1, 15));
        inventario.setProveedor(1L);

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProveedorResponse proveedor = new ProveedorResponse();
        proveedor.setNombre("Perfumeria Italiana");

        when(invRepo.findById(1L)).thenReturn(Optional.of(inventario));
        when(productoClient.obtenerProducto(anyLong(), anyString())).thenReturn(producto);
        when(sucursalClient.obtenerSucursal(anyLong(), anyString())).thenReturn(sucursal);
        when(proveedorClient.obtenerProveedor(anyLong(), anyString())).thenReturn(proveedor);

        InventarioResponse resultado = service.obtener(1L, token);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(50, resultado.getCantidad());

        verify(invRepo).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoInventarioNoExiste() {

        when(invRepo.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> service.obtener(99L, token)
        );

        assertEquals("Inventario no encontrado", ex.getMessage());

        verify(invRepo).findById(99L);
    }

    @Test
    void deberiaRetornarListaInventarios() {

        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProducto(1L);
        inventario.setSucursal(1L);
        inventario.setCantidad(50);
        inventario.setStockMinimo(10);
        inventario.setFechaInventario(LocalDate.of(2025, 1, 15));
        inventario.setProveedor(1L);

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProveedorResponse proveedor = new ProveedorResponse();
        proveedor.setNombre("Perfumeria Italiana");

        when(invRepo.findAll()).thenReturn(List.of(inventario));
        when(productoClient.obtenerProducto(anyLong(), anyString())).thenReturn(producto);
        when(sucursalClient.obtenerSucursal(anyLong(), anyString())).thenReturn(sucursal);
        when(proveedorClient.obtenerProveedor(anyLong(), anyString())).thenReturn(proveedor);

        List<InventarioResponse> resultado = service.listar(token);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());

        verify(invRepo).findAll();
    }

    @Test
    void deberiaCrearInventarioCorrectamente() {

        InventarioDTO dto = new InventarioDTO();
        dto.setProducto(1L);
        dto.setSucursal(1L);
        dto.setCantidad(50);
        dto.setStockMinimo(10);
        dto.setFechaInventario(LocalDate.of(2025, 1, 15));
        dto.setProveedor(1L);

        Inventario inventarioGuardado = new Inventario();
        inventarioGuardado.setId(1L);
        inventarioGuardado.setProducto(dto.getProducto());
        inventarioGuardado.setSucursal(dto.getSucursal());
        inventarioGuardado.setCantidad(dto.getCantidad());
        inventarioGuardado.setStockMinimo(dto.getStockMinimo());
        inventarioGuardado.setFechaInventario(dto.getFechaInventario());
        inventarioGuardado.setProveedor(dto.getProveedor());

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProveedorResponse proveedor = new ProveedorResponse();
        proveedor.setNombre("Perfumeria Italiana");

        when(productoClient.obtenerProducto(anyLong(), anyString())).thenReturn(producto);
        when(sucursalClient.obtenerSucursal(anyLong(), anyString())).thenReturn(sucursal);
        when(proveedorClient.obtenerProveedor(anyLong(), anyString())).thenReturn(proveedor);
        when(invRepo.save(any(Inventario.class))).thenReturn(inventarioGuardado);

        InventarioResponse resultado = service.crear(dto, token);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(50, resultado.getCantidad());

        verify(invRepo).save(any(Inventario.class));
    }

    @Test
    void deberiaActualizarInventarioCorrectamente() {

        Inventario existente = new Inventario();
        existente.setId(1L);
        existente.setProducto(1L);
        existente.setSucursal(1L);
        existente.setCantidad(20);
        existente.setStockMinimo(5);
        existente.setFechaInventario(LocalDate.of(2025, 1, 15));
        existente.setProveedor(1L);

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

        when(productoClient.obtenerProducto(anyLong(), anyString())).thenReturn(producto);
        when(sucursalClient.obtenerSucursal(anyLong(), anyString())).thenReturn(sucursal);
        when(proveedorClient.obtenerProveedor(anyLong(), anyString())).thenReturn(proveedor);
        when(invRepo.findById(1L)).thenReturn(Optional.of(existente));
        when(invRepo.save(any(Inventario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InventarioResponse resultado = service.actualizar(1L, dto, token);

        assertEquals(100, resultado.getCantidad());
        assertEquals(15, resultado.getStockMinimo());

        verify(invRepo).findById(1L);
        verify(invRepo).save(existente);
    }

    @Test
    void deberiaEliminarInventarioPorId() {

        doNothing().when(invRepo).deleteById(1L);

        service.eliminar(1L);

        verify(invRepo).deleteById(1L);
    }
    @Test
    void deberiaLanzarExcepcionSiProductoNoExisteAlCrear() {
        InventarioDTO dto = new InventarioDTO();
        dto.setProducto(99L);
        dto.setSucursal(1L);
        dto.setCantidad(50);
        dto.setStockMinimo(10);
        dto.setFechaInventario(LocalDate.of(2025, 1, 15));
        dto.setProveedor(1L);

        when(productoClient.obtenerProducto(anyLong(), anyString())).thenReturn(null);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.crear(dto, token)
        );

        assertEquals("Producto no existe", ex.getMessage());

        verify(invRepo, never()).save(any());
    }
}