package com.petwellness.service;

import com.petwellness.dto.ArchivoDTO;
import com.petwellness.dto.ArchivoRegistroDTO;

import java.util.List;

public interface ArchivoService {
    ArchivoRegistroDTO createArchivo(ArchivoRegistroDTO archivoRegistroDTO);
    ArchivoRegistroDTO updateArchivo(Integer id, ArchivoRegistroDTO archivoRegistroDTO);
    void deleteArchivo(Integer id);
    ArchivoDTO getArchivoById(Integer id);
    List<ArchivoDTO> getAllArchivos();
}
