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
        VeterinarioRegistroDTO veterinarioRegistroDTO = modelMapper.map(veterinario, VeterinarioRegistroDTO.class);
        /*if (veterinario.getUsuario() != null) {
            veterinarioRegistroDTO.setUserId(veterinario.getUsuario().getUserId());
            veterinarioRegistroDTO.setNombre(veterinario.getUsuario().getNombre());
            veterinarioRegistroDTO.setApellido(veterinario.getUsuario().getApellido());
            veterinarioRegistroDTO.setTelefono(veterinario.getUsuario().getTelefono());
            //veterinarioRegistroDTO.setContrasena(veterinario.getUsuario().getContrasena());
            veterinarioRegistroDTO.setTipoUsuario(veterinario.getUsuario().getTipoUsuario());
        }*/
        return veterinarioRegistroDTO;
    }


    public Veterinario toEntity(VeterinarioRegistroDTO veterinarioRegistroDTO) {
        return modelMapper.map(veterinarioRegistroDTO, Veterinario.class);
    }
}
