package com.petwellness.service;

import com.petwellness.model.entity.HorariosDisponibles;

import java.util.List;

public interface HorariosDisponiblesService {
    HorariosDisponibles agregarHorario(HorariosDisponibles horario);
    void eliminarHorario(Integer id);
    List<HorariosDisponibles> obtenerHorarios();
    List<HorariosDisponibles> obtenerHorariosPorVeterinarioId(Integer veterinarioId);
}
