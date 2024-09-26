package com.petwellness.service;

import java.util.List;

import com.petwellness.model.entity.HorariosDisponibles;

public interface HorariosDisponiblesService {
    List<HorariosDisponibles> findAll();
    HorariosDisponibles findById(Integer id);
    HorariosDisponibles update(Integer id, HorariosDisponibles horariosDisponibles);
    HorariosDisponibles create(HorariosDisponibles horariosDisponibles);
    void deleteById(Integer id);
}
