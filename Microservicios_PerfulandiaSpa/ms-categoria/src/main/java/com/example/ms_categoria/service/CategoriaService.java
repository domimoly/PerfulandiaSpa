package com.example.ms_categoria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_categoria.dto.CategoriaDTO;
import com.example.ms_categoria.model.Categoria;
import com.example.ms_categoria.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {
    private final CategoriaRepository repo;

    public Categoria crear(CategoriaDTO dto) {
        log.info("Crear categoria", keyValue("Nombre", dto.getNombre()));

        Categoria c = new Categoria(null, dto.getNombre(), dto.getDescripcion());
        return repo.save(c);
    }

    public List<Categoria> listar() {
        log.info("Listar categorias");
        return repo.findAll();
    }

    public Categoria obtener(Long id) {
        log.info("Obtener categoria", keyValue("id", id));

        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
    }

    public Categoria actualizar(Long id, CategoriaDTO dto) {
        log.info("Actualizar categoria", keyValue("id", id));

        Categoria c = obtener(id);
        c.setNombre(dto.getNombre());
        c.setDescripcion(dto.getDescripcion());

        return repo.save(c);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar categoria", keyValue("id", id));
        repo.deleteById(id);
    }
}

