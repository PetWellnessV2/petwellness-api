package com.petwellness.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petwellness.model.entity.HorariosDisponibles;
import com.petwellness.repository.HorariosDisponiblesRepository;
import com.petwellness.service.HorariosDisponiblesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HorariosDisponiblesServiceImpl implements HorariosDisponiblesService {

    private final HorariosDisponiblesRepository horariosDisponiblesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HorariosDisponibles> findAll() {
        return horariosDisponiblesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public HorariosDisponibles findById(Integer id) {
        return horariosDisponiblesRepository.findById(id).orElse(null);
    }


    @Override
    public HorariosDisponibles update(Integer id,HorariosDisponibles horariosDisponibles) {
        HorariosDisponibles horariosDisponiblesFromDb = findById(id);

        if(horariosDisponiblesFromDb == null){
            throw new IllegalArgumentException("El horario con ID " + id + " no existe");
        }

        horariosDisponiblesFromDb.setHora(horariosDisponibles.getHora());
        horariosDisponiblesFromDb.setFecha(horariosDisponibles.getFecha());
        return horariosDisponiblesRepository.save(horariosDisponiblesFromDb);
    }

    @Override
    public HorariosDisponibles create(HorariosDisponibles horariosDisponibles) {
        return horariosDisponiblesRepository.save(horariosDisponibles);
    }

    @Override
    public void deleteById(Integer id) {
        horariosDisponiblesRepository.deleteById(id);
    }



}
