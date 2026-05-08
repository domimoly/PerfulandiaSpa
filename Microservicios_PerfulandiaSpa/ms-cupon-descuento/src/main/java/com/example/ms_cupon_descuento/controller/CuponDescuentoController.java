package com.example.ms_cupon_descuento.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_cupon_descuento.dto.CuponResponseDTO;
import com.example.ms_cupon_descuento.model.CuponDescuento;
import com.example.ms_cupon_descuento.service.CuponDescuentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cupones")
@RequiredArgsConstructor
public class CuponDescuentoController {
    private final CuponDescuentoService cuponService;

    @PostMapping
    public ResponseEntity<CuponDescuento> crear(@RequestBody CuponDescuento cupon) {        
        return ResponseEntity.status(201).body(cuponService.crear(cupon));
    }

    @GetMapping
    public ResponseEntity<List<CuponDescuento>> listar() {
        return ResponseEntity.ok(cuponService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuponDescuento> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(cuponService.obtener(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuponDescuento> actualizar(
            @PathVariable Long id,
            @RequestBody CuponDescuento cupon) {
        return ResponseEntity.ok(cuponService.actualizar(id, cupon));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cuponService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/validar/{codigo}")
    public ResponseEntity<CuponResponseDTO> validar(@PathVariable String codigo) {
        // 1 | Aquí llamamos al serivico para validar el cupón
        double porcentaje = cuponService.validarYAplicarCupon(codigo);
        CuponResponseDTO response = new CuponResponseDTO(codigo, porcentaje);
        return ResponseEntity.ok(response);
    }
    

}
