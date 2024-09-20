package com.petwellness.service.impl;

import com.petwellness.model.entity.Usuario;
import com.petwellness.model.entity.Veterinario;
import com.petwellness.repository.VeterinarioRepository;
import com.petwellness.service.UsuarioService;
import com.petwellness.service.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final UsuarioService usuarioService;

    @Override
    public Veterinario crearVeterinario(Veterinario veterinario) {
        return veterinarioRepository.save(veterinario);
    }

    @Override
    public List<Veterinario> obtenerVeterinarios() {
        return veterinarioRepository.findAll();
    }

    @Override
    public void eliminarVeterinario(Integer id) {
        if (!veterinarioRepository.existsById(id)) {
            throw new RuntimeException("El veterinario no existe");
        }
        veterinarioRepository.deleteById(id);
    }

    @Override
    public Veterinario actualizarVeterinario(Integer id, Veterinario veterinarioActualizado) {
        Veterinario veterinarioExistente = veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El veterinario no existe"));

        // Actualizar la información del usuario
        Usuario usuarioExistente = veterinarioExistente.getUsuario();
        Usuario usuarioActualizado = veterinarioActualizado.getUsuario();
        if (usuarioActualizado != null) {
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setApellido(usuarioActualizado.getApellido());
            usuarioExistente.setEmail(usuarioActualizado.getEmail());
            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
            usuarioExistente.setContrasena(usuarioActualizado.getContrasena());
            usuarioService.registerUsuario(usuarioExistente);  // Actualiza el usuario
        }

        // Actualizar la información específica del veterinario
        veterinarioExistente.setEspecialidad(veterinarioActualizado.getEspecialidad());
        veterinarioExistente.setInstitucionEducativa(veterinarioActualizado.getInstitucionEducativa());

        return veterinarioRepository.save(veterinarioExistente);
    }
}
