package com.example.ms_usuario.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.ms_usuario.dto.UsuarioDTO;
import com.example.ms_usuario.model.Usuario;
import com.example.ms_usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository UsuarioRepo;

    public Usuario crear(UsuarioDTO dto) {
        log.info("Crear Usuario", keyValue("username", dto.getUsername()));

        Usuario u = new Usuario(null, dto.getNombre(), dto.getUsername(), dto.getEmail(), dto.getDireccion(),dto.getTelefono(),dto.getTipo(),dto.getPassword());
        return UsuarioRepo.save(u);
    }
    
    public List<Usuario> listar(){
        log.info("Listando usuarios");
        return UsuarioRepo.findAll();
    }

    public Usuario obtener(Long id){
        log.info("Obtener usuario", keyValue("id", id));

        return UsuarioRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
                            
    }
        

    public Usuario actualizar(Long id, UsuarioDTO dto){
        log.info("Actualizando usuario id: {}", keyValue("id", id));
        Usuario u = obtener(id);
        u.setNombre(dto.getNombre());
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setDireccion(dto.getDireccion());
        u.setTelefono(dto.getTelefono());
        u.setTipo(dto.getTipo());
        u.setPassword(dto.getPassword());
        return UsuarioRepo.save(u);

    }

    public void eliminar(Long id){
        log.warn("Eliminando usuario id: {}", keyValue("id", id));
        UsuarioRepo.deleteById(id);
    }

}
