package com.petwellness.dto;

import com.petwellness.model.enums.TipoProducto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoDTO {
    private Integer idProducto;
    private String nombreProducto;
    private String imagen;
    private String descripcion;
    private BigDecimal costo;
    private TipoProducto tipoProducto;
    private Integer stock;
}