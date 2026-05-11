package com.example.ms_reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_reserva.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

}
