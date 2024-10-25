package com.petwellness.service.impl;

import com.petwellness.dto.VeterinarioDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.VeterinarioMapper;
import com.petwellness.model.entity.Veterinario;
import com.petwellness.repository.CustomerRepository;
import com.petwellness.repository.VeterinarioRepository;
import com.petwellness.service.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final VeterinarioMapper veterinarioMapper;
    private final CustomerRepository customerRepository;

    @Transactional
    public VeterinarioDTO crearVeterinario(VeterinarioDTO veterinarioDTO) {
        customerRepository.findByNombreAndApellido(veterinarioDTO.getNombre(), veterinarioDTO.getApellido())
                .ifPresent(existingUsuario -> {
                    throw new BadRequestException("Ya existe un veterinario con el mismo nombre y apellido");
                });
        Veterinario veterinario = veterinarioMapper.toEntity(veterinarioDTO);
        veterinario = veterinarioRepository.save(veterinario);
        return veterinarioMapper.toDTO(veterinario);

    }

    @Transactional(readOnly = true)
    @Override
    public List<VeterinarioDTO> obtenerVeterinarios() {
        List<Veterinario> veterinarios = veterinarioRepository.findAll();
        return veterinarios.stream()
                .map(veterinarioMapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public void eliminarVeterinario(Integer id) {
        if (!veterinarioRepository.existsById(id)) {
            throw new RuntimeException("El veterinario no existe");
        }
        veterinarioRepository.deleteById(id);
    }

    @Transactional
    @Override
    public VeterinarioDTO actualizarVeterinario(Integer id, VeterinarioDTO VeterinarioDTO) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El veterinario con ID " + id + " no existe"));

        veterinarioRepository
                .findByNombreAndApellido(VeterinarioDTO.getNombre(), VeterinarioDTO.getApellido())
                .ifPresent(existingVeterinario -> {
                    if (!existingVeterinario.getId().equals(id)) {
                        throw new BadRequestException("Ya existe un veterinario con el mismo nombre y apellido");
                    }
                });
        // Actualizar los datos del veterinario
        veterinario.setEspecialidad(VeterinarioDTO.getEspecialidad());
        veterinario.setInstitucionEducativa(VeterinarioDTO.getInstitucionEducativa());

        veterinario = veterinarioRepository.save(veterinario);
        return veterinarioMapper.toDTO(veterinario);
    }
}
