package com.example.ms_producto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_producto.client.CategoriaClient;
import com.example.ms_producto.dto.ProductoDTO;
import com.example.ms_producto.dto.ProductoResponse;
import com.example.ms_producto.model.Producto;
import com.example.ms_producto.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoService {
    private final ProductoRepository productoRepo;
    private final CategoriaClient categoriaClient;

    public ProductoResponse crear(ProductoDTO dto, String token) {
        log.info("Crear producto", keyValue("Nombre de Producto", dto.getNombre()));

        var categoriaR = categoriaClient.obtenerCategoria(dto.getCategoria(), token);
        if (categoriaR == null) throw new RuntimeException("Categoría no existe");

        Producto p = productoRepo.save(
            new Producto(null, dto.getNombre(), dto.getDescripcion(), dto.getPrecio(), dto.getCantidad(), dto.getCategoria()));

        return mapToResponse(p, token);
    }

    public List<ProductoResponse> listar(String token) {
        log.info("Listar productos");
        return productoRepo.findAll()
                .stream()
                .map(p -> mapToResponse(p, token))
                .toList();
    }

    public ProductoResponse obtener(Long id, String token) {
        log.info("Obtener producto", keyValue("id", id));
        Producto p = productoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        return mapToResponse(p, token);
    }

    public ProductoResponse actualizar(Long id, ProductoDTO dto, String token) {
        log.info("Actualizar producto", keyValue("id", id));

        var categoriaR = categoriaClient.obtenerCategoria(dto.getCategoria(), token);
        if (categoriaR == null) throw new RuntimeException("Categoría no existe");

        Producto p = productoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio());
        p.setCantidad(dto.getCantidad());
        p.setCategoria(dto.getCategoria());

        return mapToResponse(productoRepo.save(p), token);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar producto", keyValue("id", id));
        productoRepo.deleteById(id);
    }

    private ProductoResponse mapToResponse(Producto p, String token) {
        var categoriaR = categoriaClient.obtenerCategoria(p.getCategoria(), token);
        return ProductoResponse.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .precio(p.getPrecio())
                .cantidad(p.getCantidad())
                .categoria(categoriaR)
                .build();
    }
}