package com.example.ms_proveedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_proveedor.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository <Proveedor, Long>{

}
