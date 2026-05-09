package com.example.ms_orden.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_orden.model.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long>{

}
