
package com.petwellness.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetallePedidoCreateDTO {
    private Integer idProducto;
    private Integer cantidad;
    private BigDecimal precioTotal;
}