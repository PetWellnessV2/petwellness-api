package com.petwellness.service;

import com.petwellness.dto.UsuarioDTO;
import com.petwellness.dto.UsuarioRegistroDTO;
import com.petwellness.model.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    UsuarioRegistroDTO registerUsuario(UsuarioRegistroDTO usuarioRegistroDTO);
    void deleteUsuario(Integer id);
    List<UsuarioDTO> getAllUsuarios();
    UsuarioDTO getUsuarioById(Integer id);
    UsuarioRegistroDTO updateUsuario(Integer id, UsuarioRegistroDTO usuarioRegistroDTO);
}
