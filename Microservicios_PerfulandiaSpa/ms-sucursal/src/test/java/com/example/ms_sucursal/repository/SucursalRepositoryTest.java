package com.example.ms_sucursal.repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ms_sucursal.model.Sucursal;

@DataJpaTest
@ActiveProfiles("test")
class SucursalRepositoryTest {

    @Autowired
    private SucursalRepository repository;

    @Test
    void debeGuardarSucursal() {
        Sucursal sucursal = new Sucursal(null, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias");

        Sucursal guardada = repository.save(sucursal);

        assertNotNull(guardada.getId());
        assertEquals("Barrio Meiggs", guardada.getNombre());
        assertEquals("Barrio Meiggs, Santiago", guardada.getDireccion());
        assertEquals("Lun-Sab 9:00-20:00", guardada.getHorarioApertura());
        assertEquals("Devolucion con boleta en 30 dias", guardada.getPoliticas());
    }

    @Test
    void debeBuscarSucursalPorId() {
        Sucursal sucursal = new Sucursal(null, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias");
        Sucursal guardada = repository.save(sucursal);

        Optional<Sucursal> resultado = repository.findById(guardada.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Barrio Meiggs", resultado.get().getNombre());
        assertEquals("Barrio Meiggs, Santiago", resultado.get().getDireccion());
        assertEquals("Lun-Sab 9:00-20:00", resultado.get().getHorarioApertura());
        assertEquals("Devolucion con boleta en 30 dias", resultado.get().getPoliticas());
    }

    @Test
    void debeListarSucursales() {
        repository.save(new Sucursal(null, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias"));
        repository.save(new Sucursal(null, "Concepcion", "Av. OHiggins 123, Concepcion", "Lun-Sab 10:00-19:00", "Devolucion con boleta en 30 dias"));

        List<Sucursal> resultado = repository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarSucursal() {
        Sucursal sucursal = new Sucursal(null, "Barrio Meiggs", "Barrio Meiggs, Santiago", "Lun-Sab 9:00-20:00", "Devolucion con boleta en 30 dias");
        Sucursal guardada = repository.save(sucursal);

        repository.deleteById(guardada.getId());

        Optional<Sucursal> resultado = repository.findById(guardada.getId());
        assertFalse(resultado.isPresent());
    }
}

