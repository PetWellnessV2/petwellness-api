package com.petwellness.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petwellness.service.UserAlbergueService;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;
import com.petwellness.mapper.RegistroMascotaMapper;
import com.petwellness.repository.UserAlbergueRepository;
import com.petwellness.dto.RegistroMascotaDTO;

import java.util.stream.Collectors;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserAlbergueServiceImpl implements UserAlbergueService {

    private final UserAlbergueRepository userAlbergueRepository;
    private final RegistroMascotaMapper registroMascotaMapper;



    @Override
    @Transactional(readOnly = true)
    public List<RegistroMascotaDTO> findRegistroMascotasByUserId(Integer usuarioId) {
        List<RegistroMascota> mascotas = userAlbergueRepository.findRegistroMascotasByUserId(usuarioId);
        return mascotas.stream()
                .map(registroMascotaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroMascotaDTO> findAllWithFilters(Integer usuarioId, String nombre, Especie especie, Genero genero) {
        List<RegistroMascota> mascotas = userAlbergueRepository.findAllWithFilters(usuarioId, nombre, especie, genero);
        return mascotas.stream()
                .map(registroMascotaMapper::toDTO)
                .collect(Collectors.toList());
    }

}
