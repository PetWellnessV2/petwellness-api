package com.petwellness.service;

import com.petwellness.model.entity.Veterinario;

import java.util.List;

public interface VeterinarioService {
    Veterinario crearVeterinario(Veterinario veterinario);
    Veterinario actualizarVeterinario(Integer id, Veterinario veterinario);
    void eliminarVeterinario(Integer id);
    List<Veterinario> obtenerVeterinarios();
}
