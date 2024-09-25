package com.petwellness.mapper;

import com.petwellness.dto.ArchivoRegistroDTO;
import com.petwellness.model.entity.Archivos;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ArchivoRegistroMapper {
    private final ModelMapper modelMapper;
    public ArchivoRegistroMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public ArchivoRegistroDTO toDTO(Archivos archivos) {
        ArchivoRegistroDTO archivoRegistroDTO = modelMapper.map(archivos, ArchivoRegistroDTO.class);
        archivoRegistroDTO.setIdRegistroMascota(archivos.getRegistroMascota().getIdMascota());
        return modelMapper.map(archivos, ArchivoRegistroDTO.class);
    }
    public Archivos toEntity(ArchivoRegistroDTO archivoRegistroDTO) {
        return modelMapper.map(archivoRegistroDTO, Archivos.class);
    }
}
