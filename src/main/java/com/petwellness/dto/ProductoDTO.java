package com.petwellness.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoDTO {
    private Integer idProducto;
    private String nombre;
    private String imagen;
    private String descripcion;
    private BigDecimal costo;
    private String categoriaProducto;
    private Integer stock;
}