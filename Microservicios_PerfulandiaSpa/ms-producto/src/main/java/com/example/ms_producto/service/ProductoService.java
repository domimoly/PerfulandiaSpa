package com.example.ms_producto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_producto.model.Producto;
import com.example.ms_producto.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoService {
    private final ProductoRepository productoRepo;

    public Producto crear(Producto producto){
        return productoRepo.save(producto);
    }

    public List<Producto> listar(){
        return productoRepo.findAll();
    }

    public Producto obtener(Long id){
        return productoRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    public Producto actualizar(Long id, Producto producto){
        Producto p = obtener(id);
        p.setNombre(producto.getNombre());
        p.setDescripcion(producto.getDescripcion());
        p.setPrecio(producto.getPrecio());
        return productoRepo.save(p);
    }

    public void eliminar(Long id){
        productoRepo.deleteById(id);
    }
}
