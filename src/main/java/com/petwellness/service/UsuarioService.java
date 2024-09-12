package com.petwellness.service;

import com.petwellness.model.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario registerUsuario(Usuario usuario);
    void deleteUsuario(Integer id);
    List<Usuario> getAllUsuarios();
}
