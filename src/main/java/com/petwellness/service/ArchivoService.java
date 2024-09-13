package com.petwellness.service;

import com.petwellness.model.entity.Archivos;

import java.util.List;

public interface ArchivoService {
    List<Archivos> getAllByMascotaId(Integer idMascota);
    void delete(Integer idArchivo);
}
