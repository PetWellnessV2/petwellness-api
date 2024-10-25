package com.petwellness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteVentasDTO {
    private Integer idProducto;
    private Integer idDetalle;
    private Integer cantidad;
    private BigDecimal precioTotal;
    private String nombreProducto;
    private BigDecimal costo;
    private String categoriaProducto;
    private String descripcion;
}