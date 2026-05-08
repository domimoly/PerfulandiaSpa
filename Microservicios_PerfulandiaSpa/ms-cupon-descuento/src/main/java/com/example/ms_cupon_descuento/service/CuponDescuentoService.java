package com.example.ms_cupon_descuento.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

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

    public CuponDescuento crear(CuponDescuento cupon){
        log.info("Creando cupón: {}", cupon.getCodigo());
        return CuponRepo.save(cupon);
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

    public CuponDescuento actualizar(Long id, CuponDescuento cupon){
        log.info("Actualizando cupón id: {}", id);
        CuponDescuento c = obtener(id);
        c.setCodigo(cupon.getCodigo());
        c.setPorcentajeDescuento(cupon.getPorcentajeDescuento());
        c.setFechaVencimiento(cupon.getFechaVencimiento());
        c.setActivo(cupon.isActivo());
        return CuponRepo.save(c);
    }

    public void eliminar(Long id){
        log.warn("Eliminando cupón id: {}", id);
        CuponRepo.deleteById(id);
    }

    // - + - Validación y Aplicar el cupón - + -
    public double validarYAplicarCupon(String codigo) {
        log.info("Validando cupón con código: {}", codigo);

        // 1 | Verificar si el código existe
        CuponDescuento cupon = CuponRepo.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("El código de descuento no existe"));

        // 2 ARREGLAR | Verificar si está activo
        if (!cupon.isActivo()) {
            log.error("El cupón {} no está activo", codigo);
            throw new IllegalArgumentException("El cupón ya no se encuentra activo");
        }

        // 3 | Aquí la fecha de vencimiento pasará de String a LocalDate
        // Probablemten necesite corrección ;_; Formato: "YYYY-MM-DD"
        LocalDate fechaVencimiento = LocalDate.parse(cupon.getFechaVencimiento());
        if (fechaVencimiento.isBefore(LocalDate.now())) {
            log.error("El cupón {} ha vencido", codigo);
            throw new IllegalArgumentException("El cupón ha vencido");
        }

        log.info("Cupón {} válido. Aplicando descuento de {}%", codigo, cupon.getPorcentajeDescuento());
        // 4. Retorna el porcentaje a descontar
        return cupon.getPorcentajeDescuento();
    }
}
