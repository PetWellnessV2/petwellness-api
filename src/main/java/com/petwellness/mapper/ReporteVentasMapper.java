package com.petwellness.mapper;

import com.petwellness.dto.ProductoDTO;
import com.petwellness.dto.ReporteVentasDTO;
import com.petwellness.model.entity.DetallePedido;
import org.springframework.stereotype.Component;

@Component
public class ReporteVentasMapper {

    public ReporteVentasDTO toDTO(DetallePedido detallePedido, ProductoDTO productoDTO) {
        ReporteVentasDTO reporteVentasDTO = new ReporteVentasDTO();
        reporteVentasDTO.setIdProducto(detallePedido.getIdProducto());
        reporteVentasDTO.setIdDetalle(detallePedido.getIdDetalle());
        reporteVentasDTO.setCantidad(detallePedido.getCantidad());
        reporteVentasDTO.setPrecioTotal(detallePedido.getPrecioTotal());

        // Aquí puedes usar información del ProductoDTO si es necesaria
        reporteVentasDTO.setNombreProducto(productoDTO.getNombreProducto());
        reporteVentasDTO.setDescripcion(productoDTO.getDescripcion());

        return reporteVentasDTO;
    }
}
