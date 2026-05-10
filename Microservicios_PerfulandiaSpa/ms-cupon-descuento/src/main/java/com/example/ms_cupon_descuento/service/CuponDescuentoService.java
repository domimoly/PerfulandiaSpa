package com.example.ms_cupon_descuento.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_cupon_descuento.dto.CuponDescuentoDTO;
import com.example.ms_cupon_descuento.model.CuponDescuento;
import com.example.ms_cupon_descuento.repository.CuponDescuentoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuponDescuentoService {
    private final CuponDescuentoRepository CuponRepo;

    public CuponDescuento crear(CuponDescuentoDTO dto) {
                log.info("Crear cupón de descuento", keyValue("Código de Cupón", dto.getCodigo()));


        CuponDescuento c = new CuponDescuento(null, dto.getCodigo(), dto.getPorcentajeDescuento(), dto.getFechaVencimiento(), true );
        return CuponRepo.save(c);
    }

    public List<CuponDescuento> listar(){
        log.info("Listando cupones de descuento");
        return CuponRepo.findAll();
    }

    public CuponDescuento obtener(Long id){
        log.info("Obteniendo cupón id: {}", id);
        return CuponRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cupón no encontrado"));
    }

    public CuponDescuento actualizar(Long id, CuponDescuentoDTO dto) {
        log.info("Actualizar cupón", keyValue("id", id));
        CuponDescuento c = obtener(id);
        c.setCodigo(dto.getCodigo());
        c.setPorcentajeDescuento(dto.getPorcentajeDescuento());
        c.setFechaVencimiento(dto.getFechaVencimiento());
        return CuponRepo.save(c);
    }

    public void eliminar(Long id){
        log.warn("Eliminando cupón id: {}", id);
        CuponRepo.deleteById(id);
    }
}
