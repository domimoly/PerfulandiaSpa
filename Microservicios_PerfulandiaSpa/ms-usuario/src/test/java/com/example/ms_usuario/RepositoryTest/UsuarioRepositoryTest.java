package com.example.ms_usuario.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ms_usuario.model.Usuario;
import com.example.ms_usuario.repository.UsuarioRepository;
import java.util.Optional;
import java.util.List;


@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Test
    void debeGuardarUsuario() {
        Usuario usuario = new Usuario(null, "Juan Pérez", "jperez", "juan@mail.com",
                "Av. Siempreviva 742", "+56912345678", "cliente", "pass123");
                Usuario usuarioGuardado = usuarioRepository.save(usuario);
                assertNotNull(usuarioGuardado.getId());
                assertEquals("Juan Pérez", usuarioGuardado.getNombre());
                assertEquals("jperez", usuarioGuardado.getUsername());
                assertEquals("juan@mail.com", usuarioGuardado.getEmail());
                assertEquals("cliente", usuarioGuardado.getTipo());
    }

    @Test
    void debeBuscarUsuarioPorId() {
        Usuario usuario = new Usuario(null, "Ana López", "alopez", "ana@mail.com",
                "Calle 1", "+56911111111", "admin", "pass456");
            Usuario guardado = usuarioRepository.save(usuario);
            Optional<Usuario> resultado = usuarioRepository.findById(guardado.getId());
            assertTrue(resultado.isPresent());
            assertEquals("Ana López", resultado.get().getNombre());
            assertEquals("alopez", resultado.get().getUsername());
    }

    @Test
    void debeListarUsuarios() {
        usuarioRepository.save(new Usuario(null, "Carlos Ruiz", "cruiz", "carlos@mail.com",
                "Calle 2", "+56922222222", "cliente", "pass1"));
        usuarioRepository.save(new Usuario(null, "María Soto", "msoto", "maria@mail.com",
                "Calle 3", "+56933333333", "admin", "pass2"));
        List<Usuario> resultado = usuarioRepository.findAll();
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarUsuario() {
        Usuario usuario = new Usuario(null, "Pedro Díaz", "pdiaz", "pedro@mail.com",
                "Calle 4", "+56944444444", "cliente", "pass3");
        Usuario guardado = usuarioRepository.save(usuario);
        usuarioRepository.deleteById(guardado.getId());
        Optional<Usuario> resultado = usuarioRepository.findById(guardado.getId());
        assertFalse(resultado.isPresent());
    }

}
