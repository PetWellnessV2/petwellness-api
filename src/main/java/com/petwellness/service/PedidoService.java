package com.petwellness.service;

import com.petwellness.dto.PedidoDTO;
import java.util.List;

public interface PedidoService {
    List<PedidoDTO> obtenerPedidosDeUsuario(Integer usuarioId);
    PedidoDTO crearPedido(PedidoDTO pedidoDTO);
    PedidoDTO actualizarPedido(Integer id, PedidoDTO pedidoDTO);
    void eliminarPedido(Integer id);
    PedidoDTO obtenerPedidoPorId(Integer id);
}