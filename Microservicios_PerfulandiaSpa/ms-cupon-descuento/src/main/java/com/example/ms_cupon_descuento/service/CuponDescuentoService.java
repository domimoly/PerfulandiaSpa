package com.example.ms_cupon_descuento.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_cupon_descuento.client.ProductoClient;
import com.example.ms_cupon_descuento.dto.CuponDescuentoDTO;
import com.example.ms_cupon_descuento.dto.CuponResponse;
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
    private final ProductoClient productoClient;

    public CuponResponse crear(CuponDescuentoDTO dto, String token) {
        log.info("Crear cupón de descuento", keyValue("Código de Cupón", dto.getCodigo()));
        
        var productoR = productoClient.obtenerProducto(dto.getProducto(), token);
        if (productoR == null) {
            throw new RuntimeException("Producto no existe");
        }

        CuponDescuento cupon = CuponRepo.save(
            new CuponDescuento(null, dto.getProducto(), null, dto.getPorcentajeDescuento(), dto.getFechaVencimiento(), dto.getActivo()));
        return mapToResponse(cupon, token);
    }

    public List<CuponResponse> listar(String token){
        log.info("Listar cupones de descuento");
        return CuponRepo.findAll()
                .stream()
                .map(t -> mapToResponse(t, token))
                .toList();
    }

    public CuponResponse obtener(Long id, String token){
        log.info("Obteniendo cupón id: {}", keyValue("id", id));

         CuponDescuento cupon = CuponRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cupon no encontrado"));
        return mapToResponse(cupon, token);
    }

    public CuponResponse actualizar(Long id, CuponDescuentoDTO dto, String token) {
        log.info("Actualizar cupón", keyValue("id", id));
        var producto = productoClient.obtenerProducto(dto.getProducto(), token);

        if (producto == null) {
            throw new RuntimeException("Producto no existe");
        }

        CuponDescuento c = CuponRepo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cupón no encontrado"));

        c.setCodigo(dto.getCodigo());
        c.setProducto(dto.getProducto());
        c.setPorcentajeDescuento(dto.getPorcentajeDescuento());
        c.setFechaVencimiento(dto.getFechaVencimiento());
        return mapToResponse(CuponRepo.save(c), token);
    }

    public void eliminar(Long id){
        log.warn("Eliminar cupón", keyValue("id", id));
        CuponRepo.deleteById(id);
    }

    private CuponResponse mapToResponse(CuponDescuento cupon, String token) {
        var productoR = productoClient.obtenerProducto(cupon.getProducto(), token);
        return CuponResponse.builder()
            .id(cupon.getId())
            .producto(productoR)
            .nombreProducto(productoR != null ? productoR.getNombre() : "Producto Desconocido")
            .codigo(cupon.getCodigo())
            .porcentajeDescuento(cupon.getPorcentajeDescuento())
            .fechaVencimiento(cupon.getFechaVencimiento())
            .activo(cupon.getActivo())
            .build();
    }   
}
