package com.petwellness.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.entity.Usuario;

@Component
public class RegistroMascotaMapper {
    private final ModelMapper modelMapper;
    
    public RegistroMascotaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RegistroMascotaDTO toDTO(RegistroMascota registroMascota) {
        return modelMapper.map(registroMascota, RegistroMascotaDTO.class);
    }

    public RegistroMascota toEntity(RegistroMascotaDTO registroMascotaDTO) {

            RegistroMascota registroMascota = modelMapper.map(registroMascotaDTO, RegistroMascota.class);


            if (registroMascotaDTO.getUsuarioId() != null) {
                Usuario usuario = new Usuario();
                usuario.setUserId(registroMascotaDTO.getUsuarioId());
                registroMascota.setUsuario(usuario); 
            }

        return registroMascota;
    }
}