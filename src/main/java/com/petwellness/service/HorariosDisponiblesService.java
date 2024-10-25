package com.petwellness.service;

import com.petwellness.dto.HorariosDisponiblesDTO;

import java.util.List;

public interface HorariosDisponiblesService {
    HorariosDisponiblesDTO agregarHorario(HorariosDisponiblesDTO horarioDTO);
    void eliminarHorario(Integer id);
    List<HorariosDisponiblesDTO> obtenerHorarios();
    List<HorariosDisponiblesDTO> obtenerHorariosPorUserId(Integer veterinarioId);
}
