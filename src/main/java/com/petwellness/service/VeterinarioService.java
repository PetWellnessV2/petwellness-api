package com.petwellness.service;

import com.petwellness.dto.VeterinarioDTO;
import com.petwellness.model.entity.Veterinario;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VeterinarioService {
    Veterinario crearVeterinario(Veterinario veterinarioDTO);
    List<VeterinarioDTO> obtenerVeterinarios();
    void eliminarVeterinario(Integer id);
    VeterinarioDTO actualizarVeterinario(Integer id, VeterinarioDTO veterinarioActualizadoDTO);

    @Transactional
    Veterinario actualizarVeterinario(Integer id, Veterinario veterinarioActualizado);
}
