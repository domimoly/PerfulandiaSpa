package com.example.ms_devolucion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_devolucion.model.Devolucion;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long>{
    
}
