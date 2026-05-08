package com.example.ms_cupon_descuento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_cupon_descuento.model.CuponDescuento;

@Repository
public interface CuponDescuentoRepository extends JpaRepository <CuponDescuento, Long>{
    // verificar si está bien...
    Optional<CuponDescuento> findByCodigo(String codigo);

}
