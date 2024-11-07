package com.petwellness.service;

import com.petwellness.dto.ArchivoProfileDTO;
import com.petwellness.dto.ArchivoRegistroDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArchivoService {
    ArchivoProfileDTO createArchivo(ArchivoRegistroDTO archivoRegistroDTO,  String archivoSubidoId);
    ArchivoProfileDTO updateArchivo(Integer id, ArchivoRegistroDTO archivoRegistroDTO);
    void deleteArchivo(Integer id);
    ArchivoProfileDTO getArchivoById(Integer id);
    List<ArchivoProfileDTO> getAllArchivos();
}
