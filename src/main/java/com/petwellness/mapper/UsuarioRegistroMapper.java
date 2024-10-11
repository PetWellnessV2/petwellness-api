package com.petwellness.mapper;

import com.petwellness.dto.UsuarioRegistroDTO;
import com.petwellness.model.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRegistroMapper {
    private final ModelMapper modelMapper;
    public UsuarioRegistroMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public UsuarioRegistroDTO toDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioRegistroDTO.class);
    }
    public Usuario toEntity(Usuario usuarioRegistroDTO) {
        return modelMapper.map(usuarioRegistroDTO, Usuario.class);
    }
}