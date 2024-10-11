package com.petwellness.mapper;

import com.petwellness.dto.VeterinarioDTO;
import com.petwellness.model.entity.Veterinario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VeterinarioMapper {

    private final ModelMapper modelMapper;

    public VeterinarioMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VeterinarioDTO toDTO(Veterinario veterinario) {
        return modelMapper.map(veterinario, VeterinarioDTO.class);
    }

    public Veterinario toEntity(VeterinarioDTO veterinarioDTO) {
        return modelMapper.map(veterinarioDTO, Veterinario.class);
    }
}
