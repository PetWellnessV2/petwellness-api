package com.petwellness.mapper;

import com.petwellness.dto.VeterinarioDTO;
import com.petwellness.dto.VeterinarioRegistroDTO;
import com.petwellness.model.entity.Veterinario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VeterinarioRegistroMapper {
    private final ModelMapper modelMapper;

    public VeterinarioRegistroMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VeterinarioRegistroDTO toDTO(Veterinario veterinario) {
        return modelMapper.map(veterinario, VeterinarioRegistroDTO.class);
    }

    public Veterinario toEntity(VeterinarioRegistroDTO veterinarioRegistroDTO) {
        return modelMapper.map(veterinarioRegistroDTO, Veterinario.class);
    }
}
