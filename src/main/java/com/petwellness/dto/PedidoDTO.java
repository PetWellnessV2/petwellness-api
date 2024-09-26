package com.petwellness.dto;

import com.petwellness.model.enums.EstadoPedido;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PedidoDTO {
    private Integer idPedido;
    private Integer usuarioId;
    private LocalDateTime fechaPedido;
    private EstadoPedido estado;
    private Set<DetallePedidoDTO> detalles;
}