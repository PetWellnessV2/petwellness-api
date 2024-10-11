package com.petwellness.service;

import com.petwellness.dto.PedidoDTO;
import com.petwellness.model.enums.EstadoPedido;

import java.util.List;

public interface PedidoService {
    List<PedidoDTO> obtenerPedidosDeUsuario(Integer usuarioId);
    PedidoDTO crearPedido(PedidoDTO pedidoDTO);
    PedidoDTO agregarProductoAPedido(Integer pedidoId, Integer productoId, Integer cantidad);
    PedidoDTO agregarProductoAPedidoDeUsuario(Integer usuarioId, Integer productoId, Integer cantidad);
    PedidoDTO actualizarPedido(Integer pedidoId, PedidoDTO pedidoDTO);
    void eliminarProductoDePedido(Integer pedidoId, Integer productoId);
    void eliminarPedido(Integer pedidoId);

    List<PedidoDTO> obtenerPedidosDeUsuarioPorEstado(Integer usuarioId, EstadoPedido estado);

}