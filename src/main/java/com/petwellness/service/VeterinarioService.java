package com.petwellness.service;

import com.petwellness.dto.VeterinarioDTO;

import java.util.List;

public interface VeterinarioService {
    VeterinarioDTO crearVeterinario(VeterinarioDTO veterinarioDTO);
    List<VeterinarioDTO> obtenerVeterinarios();
    void eliminarVeterinario(Integer id);
    VeterinarioDTO actualizarVeterinario(Integer id, VeterinarioDTO veterinarioActualizadoDTO);
}
