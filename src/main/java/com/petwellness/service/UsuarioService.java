package com.petwellness.service;

import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegistroDTO;
import com.petwellness.dto.UsuarioRegistroDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioRegistroDTO registerUsuario(UserRegistroDTO usuarioRegistroDTO);
    void deleteUsuario(Integer id);
    List<UserProfileDTO> getAllUsuarios();
    UserProfileDTO getUsuarioById(Integer id);
    UsuarioRegistroDTO updateUsuario(Integer id, UsuarioRegistroDTO usuarioRegistroDTO);
}
