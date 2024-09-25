package com.petwellness.dto;

import com.petwellness.model.enums.TipoProducto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductoDTO {

    private Integer idProducto;

    @NotNull
    @Size(min = 1, max = 255)
    private String nombreProducto;

    private String imagen;

    private String descripcion;

    @NotNull
    @Positive
    private Double costo;

    @NotNull
    private TipoProducto tipoProducto;

    @NotNull
    @Positive
    private Integer stock;
}
