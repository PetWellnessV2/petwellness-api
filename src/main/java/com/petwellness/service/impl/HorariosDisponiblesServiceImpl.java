package com.petwellness.service.impl;

import com.petwellness.dto.HorariosDisponiblesDTO;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.HorariosDisponiblesMapper;
import com.petwellness.model.entity.HorariosDisponibles;
import com.petwellness.model.entity.Veterinario;
import com.petwellness.repository.HorariosDisponiblesRepository;
import com.petwellness.repository.VeterinarioRepository;
import com.petwellness.service.HorariosDisponiblesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HorariosDisponiblesServiceImpl implements HorariosDisponiblesService {
    private final HorariosDisponiblesRepository horariosDisponiblesRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final HorariosDisponiblesMapper horariosDisponiblesMapper;

    @Transactional
    @Override
    public HorariosDisponiblesDTO agregarHorario(HorariosDisponiblesDTO horarioDTO) {
        Veterinario veterinario = veterinarioRepository.findById(horarioDTO.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado con id: " + horarioDTO.getVeterinarioId()));

        HorariosDisponibles horario = horariosDisponiblesMapper.toEntity(horarioDTO);
        horario.setUser(veterinario.getUser());

        HorariosDisponibles nuevoHorario = horariosDisponiblesRepository.save(horario);

        return horariosDisponiblesMapper.toDTO(nuevoHorario);
    }

    @Transactional(readOnly = true)
    @Override
    public List<HorariosDisponiblesDTO> obtenerHorarios() {
        List<HorariosDisponibles> horarios = horariosDisponiblesRepository.findAll();
        return horarios.stream()
                .map(horariosDisponiblesMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<HorariosDisponiblesDTO> obtenerHorariosPorUserId(Integer userId) {
        List<HorariosDisponibles> horarios = horariosDisponiblesRepository.findByUserId(userId);
        return horarios.stream()
                .map(horariosDisponiblesMapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public void eliminarHorario(Integer id) {
        if (!horariosDisponiblesRepository.existsById(id)) {
            throw new ResourceNotFoundException("El horario disponible no existe con id: " + id);
        }
        horariosDisponiblesRepository.deleteById(id);
    }
}
