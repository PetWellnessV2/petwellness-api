package com.petwellness.service;

import com.petwellness.dto.PedidoDTO;
import java.util.List;

public interface PedidoService {
    List<PedidoDTO> obtenerPedidosDeUsuario(Integer usuarioId);
    PedidoDTO crearPedido(PedidoDTO pedidoDTO);
    PedidoDTO agregarProductoAPedido(Integer pedidoId, Integer productoId, Integer cantidad);
    PedidoDTO actualizarPedido(Integer pedidoId, PedidoDTO pedidoDTO);
    void eliminarProductoDePedido(Integer pedidoId, Integer productoId);
    void eliminarPedido(Integer pedidoId);
}