package com.petwellness.service.impl;

import com.petwellness.model.entity.Usuario;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public Usuario registerUsuario(Usuario usuario) {
        // Verifica si el email ya está registrado cuando es un nuevo usuario
        if(usuario.getUserId() == null) { // Nuevo usuario
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                throw new RuntimeException("El email ya está registrado");
            }
        } else { // Actualización de un usuario existente
            Optional<Usuario> existingUsuario = usuarioRepository.findById(usuario.getUserId());
            if (existingUsuario.isPresent()) {
                Usuario usuarioActual = existingUsuario.get();
                // Solo verifica si el email ha sido cambiado
                if (!usuarioActual.getEmail().equals(usuario.getEmail()) &&
                        usuarioRepository.existsByEmail(usuario.getEmail())) {
                    throw new RuntimeException("El email ya está registrado por otro usuario");
                }
            } else {
                throw new RuntimeException("El usuario no existe");
            }
        }

        usuario.setUpdatedAt(LocalDateTime.now());
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

    @Transactional
    @Override
    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }


}
