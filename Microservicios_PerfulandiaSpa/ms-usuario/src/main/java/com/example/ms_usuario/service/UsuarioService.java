package com.example.ms_usuario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_usuario.model.Usuario;
import com.example.ms_usuario.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository UsuarioRepo;

    public Usuario crear(Usuario usuario){
        log.info("Creando usuario: {}", usuario.getUsername());
        return UsuarioRepo.save(usuario);
    }

    public List<Usuario> listar(){
        log.info("Listando usuarios");
        return UsuarioRepo.findAll();
    }

    public Usuario obtener(Long id){
        log.info("Obteniendo usuario id: {}", id);
        return UsuarioRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    public Usuario actualizar(Long id, Usuario usuario){
        log.info("Actualizando usuario id: {}");
        Usuario u = obtener(id);
        u.setNombre(usuario.getNombre());
        u.setUsername(usuario.getUsername());
        u.setEmail(usuario.getEmail());
        u.setDireccion(usuario.getDireccion());
        u.setTelefono(usuario.getTelefono());
        u.setTipo(usuario.getTipo());
        u.setPassword(usuario.getPassword());
        return UsuarioRepo.save(u);

    }

    public void eliminar(Long id){
        log.warn("Eliminando usuario id: {}", id);
        UsuarioRepo.deleteById(id);
    }

}
