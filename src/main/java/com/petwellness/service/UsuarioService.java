package com.petwellness.service;

import com.petwellness.model.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario registerUsuario(Usuario usuario);
    void deleteUsuario(Integer id);
    List<Usuario> getAllUsuarios();
    Usuario updateUsuario(Integer id, Usuario usuario);
    Optional<Usuario> getUsuarioById(Integer id);
}
