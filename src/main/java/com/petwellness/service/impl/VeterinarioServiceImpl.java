package com.petwellness.service.impl;

import com.petwellness.model.entity.Veterinario;
import com.petwellness.repository.VeterinarioRepository;
import com.petwellness.service.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    @Override
    public Veterinario crearVeterinario(Veterinario veterinario) {
        return veterinarioRepository.save(veterinario);
    }

    @Override
    public Veterinario actualizarVeterinario(Integer id, Veterinario veterinario) {
        Optional<Veterinario> veterinarioExistente = veterinarioRepository.findById(id);
        if (veterinarioExistente.isPresent()) {
            Veterinario actualizado = veterinarioExistente.get();
            actualizado.setEspecialidad(veterinario.getEspecialidad());
            actualizado.setInstitucionEducativa(veterinario.getInstitucionEducativa());
            return veterinarioRepository.save(actualizado);
        } else {
            throw new RuntimeException("Veterinario no encontrado");
        }
    }

    @Override
    public void eliminarVeterinario(Integer id) {
        veterinarioRepository.deleteById(id);
    }

    @Override
    public List<Veterinario> obtenerVeterinarios() {
        return veterinarioRepository.findAll();
    }
}
