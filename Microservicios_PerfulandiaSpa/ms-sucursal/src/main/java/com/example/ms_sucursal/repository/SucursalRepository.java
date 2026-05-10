package com.example.ms_sucursal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_sucursal.model.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long>{

}
