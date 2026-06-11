package com.example.ms_proveedor.serviceTest;

import com.example.ms_proveedor.client.ProductoClient;
import com.example.ms_proveedor.client.SucursalClient;
import com.example.ms_proveedor.dto.ProductoResponse;
import com.example.ms_proveedor.dto.ProveedorDTO;
import com.example.ms_proveedor.dto.ProveedorResponse;
import com.example.ms_proveedor.dto.SucursalResponse;
import com.example.ms_proveedor.model.Proveedor;
import com.example.ms_proveedor.repository.ProveedorRepository;
import com.example.ms_proveedor.service.ProveedorService;
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
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepo;

    @Mock
    private SucursalClient sucursalClient;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private ProveedorService service;

    private final String token = "Bearer test";

    @Test
    void deberiaRetornarProveedorCuandoExiste() {

        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setNombre("Perfumeria Italiana");
        proveedor.setEmail("contacto@perfumeriaitaliana.cl");
        proveedor.setTelefono("+56975842043");
        proveedor.setDireccion("Av. Italia 1439");
        proveedor.setSucursal(1L);
        proveedor.setProducto(1L);

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        when(proveedorRepo.findById(1L)).thenReturn(Optional.of(proveedor));
        when(sucursalClient.obtenerSucursal(anyLong(), anyString())).thenReturn(sucursal);
        when(productoClient.obtenerProducto(anyLong(), anyString())).thenReturn(producto);

        ProveedorResponse resultado = service.obtener(1L, token);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Perfumeria Italiana", resultado.getNombre());

        verify(proveedorRepo).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoProveedorNoExiste() {

        when(proveedorRepo.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> service.obtener(99L, token)
        );

        assertEquals("Proveedor no encontrado", ex.getMessage());

        verify(proveedorRepo).findById(99L);
    }

    @Test
    void deberiaRetornarListaProveedores() {

        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setNombre("Perfumeria Italiana");
        proveedor.setEmail("contacto@perfumeriaitaliana.cl");
        proveedor.setTelefono("+56975842043");
        proveedor.setDireccion("Av. Italia 1439");
        proveedor.setSucursal(1L);
        proveedor.setProducto(1L);

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        when(proveedorRepo.findAll()).thenReturn(List.of(proveedor));
        when(sucursalClient.obtenerSucursal(anyLong(), anyString())).thenReturn(sucursal);
        when(productoClient.obtenerProducto(anyLong(), anyString())).thenReturn(producto);

        List<ProveedorResponse> resultado = service.listar(token);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Perfumeria Italiana", resultado.get(0).getNombre());

        verify(proveedorRepo).findAll();
    }

    @Test
    void deberiaCrearProveedorCorrectamente() {

        ProveedorDTO dto = new ProveedorDTO();
        dto.setNombre("Perfumeria Italiana");
        dto.setEmail("contacto@perfumeriaitaliana.cl");
        dto.setTelefono("+56975842043");
        dto.setDireccion("Av. Italia 1439");
        dto.setSucursal(1L);
        dto.setProducto(1L);

        Proveedor proveedorGuardado = new Proveedor();
        proveedorGuardado.setId(1L);
        proveedorGuardado.setNombre(dto.getNombre());
        proveedorGuardado.setEmail(dto.getEmail());
        proveedorGuardado.setTelefono(dto.getTelefono());
        proveedorGuardado.setDireccion(dto.getDireccion());
        proveedorGuardado.setSucursal(dto.getSucursal());
        proveedorGuardado.setProducto(dto.getProducto());

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        when(sucursalClient.obtenerSucursal(anyLong(), anyString())).thenReturn(sucursal);
        when(productoClient.obtenerProducto(anyLong(), anyString())).thenReturn(producto);
        when(proveedorRepo.save(any(Proveedor.class))).thenReturn(proveedorGuardado);

        ProveedorResponse resultado = service.crear(dto, token);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Perfumeria Italiana", resultado.getNombre());

        verify(proveedorRepo).save(any(Proveedor.class));
    }

    @Test
    void deberiaActualizarProveedorCorrectamente() {

        Proveedor existente = new Proveedor();
        existente.setId(1L);
        existente.setNombre("Proveedor viejo");
        existente.setEmail("viejo@test.cl");
        existente.setTelefono("111111111");
        existente.setDireccion("Direccion vieja");
        existente.setSucursal(1L);
        existente.setProducto(1L);

        ProveedorDTO dto = new ProveedorDTO();
        dto.setNombre("Proveedor nuevo");
        dto.setEmail("nuevo@test.cl");
        dto.setTelefono("999999999");
        dto.setDireccion("Direccion nueva");
        dto.setSucursal(1L);
        dto.setProducto(1L);

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        ProductoResponse producto = new ProductoResponse();
        producto.setNombre("Dior Sauvage");

        when(sucursalClient.obtenerSucursal(anyLong(), anyString())).thenReturn(sucursal);
        when(productoClient.obtenerProducto(anyLong(), anyString())).thenReturn(producto);
        when(proveedorRepo.findById(1L)).thenReturn(Optional.of(existente));
        when(proveedorRepo.save(any(Proveedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProveedorResponse resultado = service.actualizar(1L, dto, token);

        assertEquals("Proveedor nuevo", resultado.getNombre());
        assertEquals("nuevo@test.cl", resultado.getEmail());

        verify(proveedorRepo).findById(1L);
        verify(proveedorRepo).save(existente);
    }

    @Test
    void deberiaEliminarProveedorPorId() {

        doNothing().when(proveedorRepo).deleteById(1L);

        service.eliminar(1L);

        verify(proveedorRepo).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionSiSucursalNoExisteAlCrear() {

        ProveedorDTO dto = new ProveedorDTO();
        dto.setNombre("Proveedor test");
        dto.setEmail("test@test.cl");
        dto.setTelefono("999999999");
        dto.setDireccion("Direccion test");
        dto.setSucursal(99L);
        dto.setProducto(1L);

        when(sucursalClient.obtenerSucursal(anyLong(), anyString())).thenReturn(null);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.crear(dto, token)
        );

        assertEquals("Sucursal no existe", ex.getMessage());

        verify(proveedorRepo, never()).save(any());
    }

    @Test
    void deberiaLanzarExcepcionSiProductoNoExisteAlCrear() {

        ProveedorDTO dto = new ProveedorDTO();
        dto.setNombre("Proveedor test");
        dto.setEmail("test@test.cl");
        dto.setTelefono("999999999");
        dto.setDireccion("Direccion test");
        dto.setSucursal(1L);
        dto.setProducto(99L);

        SucursalResponse sucursal = new SucursalResponse();
        sucursal.setNombre("Barrio Meiggs");

        when(sucursalClient.obtenerSucursal(anyLong(), anyString())).thenReturn(sucursal);
        when(productoClient.obtenerProducto(anyLong(), anyString())).thenReturn(null);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.crear(dto, token)
        );

        assertEquals("Producto no existe", ex.getMessage());

        verify(proveedorRepo, never()).save(any());
    }
}