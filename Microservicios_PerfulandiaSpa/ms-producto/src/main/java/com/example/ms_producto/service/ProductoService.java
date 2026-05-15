package com.example.ms_producto.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.ms_producto.dto.ProductoDTO;
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
    
    public Producto crear(ProductoDTO dto) {
        log.info("Crear producto", keyValue("Nombre de Producto", dto.getNombre()));
        Producto p = new Producto(null, dto.getNombre(), dto.getDescripcion(), dto.getPrecio(), dto.getCantidad());
        return productoRepo.save(p); 
    } 

    public List<Producto> listar() {
        log.info("Listar productos");
        return productoRepo.findAll();
    }

    public Producto obtener(Long id) {
        log.info("Obtener producto", keyValue("id", id));
        return productoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada"));
    }

    public Producto actualizar(Long id, ProductoDTO dto){
        log.info("Actualizar orden", keyValue("id", id));
        Producto p = obtener(id);
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio());
        p.setCantidad(dto.getCantidad());
        return productoRepo.save(p);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar producto", keyValue("id", id));
        productoRepo.deleteById(id);
    }
}
