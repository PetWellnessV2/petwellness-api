package com.petwellness.service.impl;

import com.petwellness.model.entity.HorariosDisponibles;
import com.petwellness.repository.HorariosDisponiblesRepository;
import com.petwellness.service.HorariosDisponiblesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HorariosDisponiblesServiceImpl implements HorariosDisponiblesService {
    private final HorariosDisponiblesRepository horariosDisponiblesRepository;


    @Override
    public HorariosDisponibles agregarHorario(HorariosDisponibles horario) {
        return horariosDisponiblesRepository.save(horario);
    }

    @Override
    public void eliminarHorario(Integer id) {
        horariosDisponiblesRepository.deleteById(id);

    }

    @Override
    public List<HorariosDisponibles> obtenerHorarios() {
        return horariosDisponiblesRepository.findAll();
    }

    @Override
    public List<HorariosDisponibles> obtenerHorariosPorVeterinarioId(Integer userId) {
        return horariosDisponiblesRepository.findByVeterinarioUsuarioUserId(userId);
    }
}
