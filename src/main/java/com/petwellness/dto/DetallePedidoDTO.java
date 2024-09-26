package com.petwellness.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetallePedidoDTO {
    private Integer idDetalle;
    private Integer idPedido;
    private Integer idProducto;
    private Integer cantidad;
    private BigDecimal precioTotal;
}