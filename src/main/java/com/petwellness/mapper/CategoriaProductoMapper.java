package com.petwellness.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petwellness.dto.CategoriaProductoDTO;
import com.petwellness.model.entity.CategoriaProducto;

@Component
public class CategoriaProductoMapper {
        private final ModelMapper modelMapper;

    public CategoriaProductoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoriaProductoDTO toDTO(CategoriaProducto categoriaProducto) {
        return modelMapper.map(categoriaProducto, CategoriaProductoDTO.class);
    }

    public CategoriaProducto toEntity(CategoriaProductoDTO categoriaProductoDTO) {
        return modelMapper.map(categoriaProductoDTO, CategoriaProducto.class);
    }
}
