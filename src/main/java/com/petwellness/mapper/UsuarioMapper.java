package com.petwellness.mapper;

import com.petwellness.dto.UsuarioDTO;
import com.petwellness.model.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    private final ModelMapper modelMapper;
    public UsuarioMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public UsuarioDTO toDTO(Customer usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
    public Customer toEntity(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Customer.class);
    }
}
