package com.petwellness.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoCreateDTO {
    private Integer usuarioId;
    private List<DetallePedidoCreateDTO> detalles;
}
