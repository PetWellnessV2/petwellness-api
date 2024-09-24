package com.petwellness.service;

import com.petwellness.dto.ArchivoDTO;

import java.util.List;

public interface ArchivoService {
    ArchivoDTO createArchivo(ArchivoDTO archivoDTO);
    ArchivoDTO updateArchivo(Integer id, ArchivoDTO archivoDTO);
    void deleteArchivo(Integer id);
    ArchivoDTO getArchivoById(Integer id);
    List<ArchivoDTO> getAllArchivos();
}
