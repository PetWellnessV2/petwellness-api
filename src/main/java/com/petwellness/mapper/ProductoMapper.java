package com.petwellness.mapper;

import com.petwellness.dto.ProductoDTO;
import com.petwellness.model.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        if (producto == null) {
            return null;
        }

        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setImagen(producto.getImagen());
        dto.setDescripcion(producto.getDescripcion());
        dto.setCosto(producto.getCosto());
        dto.setTipoProducto(producto.getTipoProducto());
        dto.setStock(producto.getStock());
        return dto;
    }

}