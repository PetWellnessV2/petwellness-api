package com.petwellness.service.impl;

import com.petwellness.dto.PedidoCreateDTO;
import com.petwellness.dto.PedidoDTO;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.PedidoMapper;
import com.petwellness.model.entity.DetallePedido;
import com.petwellness.model.entity.Pedido;
import com.petwellness.model.entity.Producto;
import com.petwellness.model.enums.EstadoPedido;
import com.petwellness.repository.PedidoRepository;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    @Transactional
    public PedidoDTO crearPedido(PedidoCreateDTO pedidoCreateDTO) {
        usuarioRepository.findById(pedidoCreateDTO.getUsuarioId())
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Pedido pedido = pedidoMapper.toPedidoEntity(pedidoCreateDTO);
        
        pedido.getDetalles().forEach(detalle -> {
            Producto producto = productoRepository.findById(detalle.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + detalle.getIdProducto()));
                
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombreProducto());
            }
            
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
        });

        pedido.calcularPrecioTotal();
        Pedido savedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(savedPedido);
    }

    @Override
    public List<PedidoDTO> obtenerPedidosDeUsuario(Integer usuarioId) {
        usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);
        return pedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoDTO obtenerPedidoPorId(Integer pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        return null;
    }

    @Override
    @Transactional
    public PedidoDTO agregarProductoAPedido(Integer pedidoId, Integer productoId, Integer cantidad) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
                
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setPedido(pedido);
        detallePedido.setIdProducto(productoId);
        detallePedido.setCantidad(cantidad);
        detallePedido.setPrecioTotal(producto.getCosto().multiply(BigDecimal.valueOf(cantidad)));

        pedido.getDetalles().add(detallePedido);
        pedido.calcularPrecioTotal();

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);

        Pedido updatedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(updatedPedido);
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
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

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

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);

        pedido.calcularPrecioTotal();
        Pedido updatedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(updatedPedido);
    }

    @Override
    @Transactional
    public PedidoDTO actualizarPedido(Integer pedidoId, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
        
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.getDetalles().clear();
        pedido.getDetalles().addAll(pedidoDTO.getDetalles().stream()
                .map(detalleDTO -> pedidoMapper.toEntity(detalleDTO, pedido))
                .collect(Collectors.toList()));
        
        pedido.calcularPrecioTotal();
        Pedido updatedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(updatedPedido);
    }

    @Override
    @Transactional
    public void eliminarProductoDePedido(Integer pedidoId, Integer productoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
        
        DetallePedido detalle = pedido.getDetalles().stream()
                .filter(d -> d.getIdProducto().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado en el pedido"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        producto.setStock(producto.getStock() + detalle.getCantidad());
        productoRepository.save(producto);
        
        pedido.getDetalles().remove(detalle);
        pedido.calcularPrecioTotal();
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void eliminarPedido(Integer pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));

        pedido.getDetalles().forEach(detalle -> {
            Producto producto = productoRepository.findById(detalle.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        });

        pedidoRepository.delete(pedido);
    }

    @Override
    public List<PedidoDTO> obtenerPedidosDeUsuarioPorEstado(Integer usuarioId, EstadoPedido estado) {
        usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            
        List<Pedido> pedidos = pedidoRepository.findByUsuarioIdAndEstadoOrderByFechaPedidoDesc(usuarioId, estado);
        return pedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }
}