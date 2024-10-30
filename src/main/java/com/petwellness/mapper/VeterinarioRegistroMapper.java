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
        if (veterinario.getVet() != null) {
            veterinarioRegistroDTO.setUserId(veterinario.getVet().getUserId());
            veterinarioRegistroDTO.setNombre(veterinario.getVet().getCustomer().getNombre());
            veterinarioRegistroDTO.setApellido(veterinario.getVet().getCustomer().getApellido());
            veterinarioRegistroDTO.setTelefono(veterinario.getVet().getCustomer().getTelefono());
            veterinarioRegistroDTO.setContrasena(veterinario.getVet().getContrasena());
            veterinarioRegistroDTO.setTipoUsuario(veterinario.getVet().getCustomer().getTipoUsuario());
        }
        return veterinarioRegistroDTO;
    }

    public Veterinario toEntity(VeterinarioRegistroDTO veterinarioRegistroDTO) {
        return modelMapper.map(veterinarioRegistroDTO, Veterinario.class);
    }
}
