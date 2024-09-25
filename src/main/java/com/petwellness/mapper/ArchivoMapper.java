package com.petwellness.mapper;

import com.petwellness.dto.ArchivoDTO;
import com.petwellness.model.entity.Archivos;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ArchivoMapper {
    private final ModelMapper modelMapper;
    public ArchivoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public ArchivoDTO toDTO(Archivos archivos) {
        return modelMapper.map(archivos, ArchivoDTO.class);
    }
    public Archivos toEntity(ArchivoDTO archivoDTO) {
        return modelMapper.map(archivoDTO, Archivos.class);
    }
}
