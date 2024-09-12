package com.petwellness.service.impl;

import com.petwellness.model.entity.Usuario;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public Usuario registerUsuario(Usuario usuario) {
        if(usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        usuario.setCreatedAt(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public void deleteUsuario(Integer id) {
        if(!usuarioRepository.existsById(id)) {
            throw new RuntimeException("El usuario no existe");
        }
        usuarioRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
}
