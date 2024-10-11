package com.petwellness.service.impl;

import com.petwellness.dto.DetallePedidoDTO;
import com.petwellness.dto.PedidoDTO;
import com.petwellness.model.entity.DetallePedido;
import com.petwellness.model.entity.Pedido;
import com.petwellness.model.entity.Producto;
import com.petwellness.model.enums.EstadoPedido;
import com.petwellness.repository.DetallePedidoRepository;
import com.petwellness.repository.PedidoRepository;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<PedidoDTO> obtenerPedidosDeUsuario(Integer usuarioId) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);
        return pedidos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setUsuarioId(pedidoDTO.getUsuarioId());
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(pedidoDTO.getEstado());
        
        List<DetallePedido> detalles = pedidoDTO.getDetalles().stream()
            .map(detalleDTO -> {
                DetallePedido detalle = new DetallePedido();
                detalle.setPedido(pedido);
                detalle.setIdProducto(detalleDTO.getIdProducto());
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setPrecioTotal(detalleDTO.getPrecioTotal());
                return detalle;
            })
            .collect(Collectors.toList());
        
        pedido.setDetalles(detalles);
        
        Pedido savedPedido = pedidoRepository.save(pedido);
        return mapToDTO(savedPedido);
    }

    @Override
    @Transactional
    public PedidoDTO agregarProductoAPedidoDeUsuario(Integer usuarioId, Integer productoId, Integer cantidad) {
        Pedido pedido = pedidoRepository.findTopByUsuarioIdAndEstadoOrderByFechaPedidoDesc(usuarioId, EstadoPedido.PENDIENTE)
                .orElseGet(() -> {
                    Pedido newPedido = new Pedido();
                    newPedido.setUsuarioId(usuarioId);
                    newPedido.setFechaPedido(LocalDateTime.now());
                    newPedido.setEstado(EstadoPedido.PENDIENTE);
                    return pedidoRepository.save(newPedido);
                });

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetallePedido detallePedido = pedido.getDetalles().stream()
                .filter(d -> d.getIdProducto().equals(productoId))
                .findFirst()
                .orElseGet(() -> {
                    DetallePedido newDetalle = new DetallePedido();
                    newDetalle.setPedido(pedido);
                    newDetalle.setIdProducto(productoId);
                    newDetalle.setCantidad(0);
                    newDetalle.setPrecioTotal(BigDecimal.ZERO);
                    pedido.getDetalles().add(newDetalle);
                    return newDetalle;
                });

        detallePedido.setCantidad(detallePedido.getCantidad() + cantidad);
        detallePedido.setPrecioTotal(producto.getCosto().multiply(BigDecimal.valueOf(detallePedido.getCantidad())));

        Pedido updatedPedido = pedidoRepository.save(pedido);
        return mapToDTO(updatedPedido);
    }

    @Override
    @Transactional
    public PedidoDTO agregarProductoAPedido(Integer pedidoId, Integer productoId, Integer cantidad) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setPedido(pedido);
        detallePedido.setIdProducto(productoId);
        detallePedido.setCantidad(cantidad);
        detallePedido.setPrecioTotal(producto.getCosto().multiply(BigDecimal.valueOf(cantidad)));

        pedido.getDetalles().add(detallePedido);
        Pedido updatedPedido = pedidoRepository.save(pedido);
        return mapToDTO(updatedPedido);
    }

    @Override
    @Transactional
    public PedidoDTO actualizarPedido(Integer pedidoId, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        pedido.setEstado(pedidoDTO.getEstado());
        
        List<DetallePedido> nuevosDetalles = new ArrayList<>();
        
        for (DetallePedidoDTO detalleDTO : pedidoDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalleDTO.getIdProducto()));
            
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setIdProducto(detalleDTO.getIdProducto());
            detalle.setCantidad(detalleDTO.getCantidad());
            
            BigDecimal precioTotal = producto.getCosto().multiply(BigDecimal.valueOf(detalleDTO.getCantidad()));
            detalle.setPrecioTotal(precioTotal);
            
            nuevosDetalles.add(detalle);
        }
        
        pedido.getDetalles().clear();
        pedido.getDetalles().addAll(nuevosDetalles);
        
        Pedido updatedPedido = pedidoRepository.save(pedido);
        return mapToDTO(updatedPedido);
    }

    @Override
    @Transactional
    public void eliminarProductoDePedido(Integer pedidoId, Integer productoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        boolean removed = pedido.getDetalles().removeIf(detalle -> detalle.getIdProducto().equals(productoId));
        
        if (!removed) {
            throw new RuntimeException("Producto no encontrado en el pedido");
        }
        
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void eliminarPedido(Integer pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedidoRepository.delete(pedido);
    }

    @Override
    public List<PedidoDTO> obtenerPedidosDeUsuarioPorEstado(Integer usuarioId, EstadoPedido estado) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioIdAndEstadoOrderByFechaPedidoDesc(usuarioId, estado);
        return pedidos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PedidoDTO mapToDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(pedido.getIdPedido());
        dto.setUsuarioId(pedido.getUsuarioId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());
        dto.setDetalles(pedido.getDetalles().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList()));
        return dto;
    }

    private DetallePedidoDTO mapToDTO(DetallePedido detallePedido) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setIdDetalle(detallePedido.getIdDetalle());
        dto.setIdPedido(detallePedido.getPedido().getIdPedido());
        dto.setIdProducto(detallePedido.getIdProducto());
        dto.setCantidad(detallePedido.getCantidad());
        dto.setPrecioTotal(detallePedido.getPrecioTotal());
        return dto;
    }
}