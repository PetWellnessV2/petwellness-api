package com.petwellness.service.impl;

import com.petwellness.model.entity.Archivos;
import com.petwellness.repository.ArchivoRepository;
import com.petwellness.service.ArchivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchivoServiceImpl implements ArchivoService {

    private final ArchivoRepository archivoRepository;


    @Override
    public List<Archivos> getAllByMascotaId(Integer idMascota) {
        return archivoRepository.findByRegistroMascotaIdMascota(idMascota);
    }

    @Override
    public void delete(Integer idArchivo) {
        archivoRepository.deleteById(idArchivo);
    }
}

