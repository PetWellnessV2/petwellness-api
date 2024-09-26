package com.petwellness.service.impl;

import com.petwellness.dto.PedidoDTO;
import com.petwellness.dto.DetallePedidoDTO;
import com.petwellness.model.entity.Pedido;
import com.petwellness.model.entity.DetallePedido;
import com.petwellness.model.entity.Usuario;
import com.petwellness.model.entity.Producto;
import com.petwellness.repository.PedidoRepository;
import com.petwellness.repository.DetallePedidoRepository;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<PedidoDTO> obtenerPedidosDeUsuario(Integer usuarioId) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioUserId(usuarioId);
        return pedidos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        Usuario usuario = usuarioRepository.findById(pedidoDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(pedidoDTO.getEstado());
        
        pedido = pedidoRepository.save(pedido);
        
        for (DetallePedidoDTO detalleDTO : pedidoDTO.getDetalles()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(productoRepository.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado")));
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioTotal(detalleDTO.getPrecioTotal());
            detallePedidoRepository.save(detalle);
        }
        
        return mapToDTO(pedido);
    }

    @Override
    @Transactional
    public PedidoDTO actualizarPedido(Integer id, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(pedidoDTO.getEstado());
        pedido = pedidoRepository.save(pedido);
        return mapToDTO(pedido);
    }

    @Override
    @Transactional
    public void eliminarPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public PedidoDTO obtenerPedidoPorId(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return mapToDTO(pedido);
    }

    private PedidoDTO mapToDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(pedido.getIdPedido());
        dto.setUsuarioId(pedido.getUsuario().getUserId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());
        dto.setDetalles(pedido.getDetalles().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toSet()));
        return dto;
    }

    private DetallePedidoDTO mapToDTO(DetallePedido detalle) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setIdDetalle(detalle.getIdDetalle());
        dto.setIdPedido(detalle.getPedido().getIdPedido());
        dto.setIdProducto(detalle.getProducto().getIdProducto());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioTotal(detalle.getPrecioTotal());
        return dto;
    }
}