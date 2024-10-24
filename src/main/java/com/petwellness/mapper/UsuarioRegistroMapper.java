package com.petwellness.mapper;

import com.petwellness.dto.UsuarioRegistroDTO;
import com.petwellness.model.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRegistroMapper {
    private final ModelMapper modelMapper;
    public UsuarioRegistroMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public UsuarioRegistroDTO toDTO(Customer usuario) {
        return modelMapper.map(usuario, UsuarioRegistroDTO.class);
    }
    public Customer toEntity(UsuarioRegistroDTO usuarioRegistroDTO) {
        return modelMapper.map(usuarioRegistroDTO, Customer.class);
    }
}