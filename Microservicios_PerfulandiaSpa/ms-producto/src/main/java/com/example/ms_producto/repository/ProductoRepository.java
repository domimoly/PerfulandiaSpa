package com.example.ms_producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_producto.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
