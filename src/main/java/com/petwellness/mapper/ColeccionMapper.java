package com.petwellness.mapper;

import com.petwellness.dto.ColeccionDTO;
import com.petwellness.model.entity.Coleccion;
import com.petwellness.model.entity.ProductoColeccion;
import com.petwellness.model.entity.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ColeccionMapper {

    public ColeccionDTO toDTO(Coleccion coleccion) {
        if (coleccion == null) {
            return null;
        }

        ColeccionDTO dto = new ColeccionDTO();
        dto.setId(coleccion.getId());
        dto.setNombre(coleccion.getNombre());
        dto.setUsuarioId(coleccion.getUsuarioId());
        dto.setProductosIds(coleccion.getProductosColeccion().stream()
                .map(pc -> pc.getProducto().getIdProducto())
                .collect(Collectors.toSet()));
        return dto;
    }

    public Coleccion toEntity(ColeccionDTO dto) {
        if (dto == null) {
            return null;
        }

        Coleccion coleccion = new Coleccion();
        coleccion.setId(dto.getId());
        coleccion.setNombre(dto.getNombre());
        coleccion.setUsuarioId(dto.getUsuarioId());
        return coleccion;
    }

    public ProductoColeccion createProductoColeccion(Coleccion coleccion, Producto producto) {
        ProductoColeccion productoColeccion = new ProductoColeccion();
        productoColeccion.setColeccion(coleccion);
        productoColeccion.setProducto(producto);
        return productoColeccion;
    }
}