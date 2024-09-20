package com.petwellness.service;

import com.petwellness.model.entity.Veterinario;

import java.util.List;

public interface VeterinarioService {
    Veterinario crearVeterinario(Veterinario veterinario);
    List<Veterinario> obtenerVeterinarios();
    void eliminarVeterinario(Integer id);
    Veterinario actualizarVeterinario(Integer id, Veterinario veterinarioActualizado);
}
