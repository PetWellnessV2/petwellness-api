package com.petwellness.mapper;

import com.petwellness.dto.DetallePedidoCreateDTO;
import com.petwellness.dto.DetallePedidoDTO;
import com.petwellness.dto.PedidoCreateDTO;
import com.petwellness.dto.PedidoDTO;
import com.petwellness.model.entity.DetallePedido;
import com.petwellness.model.entity.Pedido;
import com.petwellness.model.entity.Usuario;
import com.petwellness.model.enums.EstadoPedido;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoMapper(ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Pedido toPedidoEntity(PedidoCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }

        Pedido pedido = new Pedido();
        pedido.setUsuarioId(createDTO.getUsuarioId());
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        
        if (createDTO.getDetalles() != null) {
            pedido.setDetalles(createDTO.getDetalles().stream()
                    .map(detalleDTO -> toDetallePedidoEntity(detalleDTO, pedido))
                    .collect(Collectors.toList()));
        }

        return pedido;
    }

    private DetallePedido toDetallePedidoEntity(DetallePedidoCreateDTO createDTO, Pedido pedido) {
        if (createDTO == null) {
            return null;
        }

        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setPedido(pedido);
        detallePedido.setIdProducto(createDTO.getIdProducto());
        detallePedido.setCantidad(createDTO.getCantidad());
        detallePedido.setPrecioTotal(createDTO.getPrecio().multiply(BigDecimal.valueOf(createDTO.getCantidad())));

        return detallePedido;
    }

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(pedido.getIdPedido());
        dto.setUsuarioId(pedido.getUsuarioId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());
        dto.setPrecioTotalPedido(pedido.getPrecioTotalPedido());
        
        usuarioRepository.findById(pedido.getUsuarioId())
            .ifPresent(usuario -> dto.setNombreUsuario(
                usuario.getNombre() + " " + usuario.getApellido()
            ));

        dto.setDetalles(pedido.getDetalles().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    public DetallePedidoDTO toDTO(DetallePedido detallePedido) {
        if (detallePedido == null) {
            return null;
        }

        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setIdDetalle(detallePedido.getIdDetalle());
        dto.setIdPedido(detallePedido.getPedido().getIdPedido());
        dto.setIdProducto(detallePedido.getIdProducto());
        dto.setCantidad(detallePedido.getCantidad());
        dto.setPrecioTotal(detallePedido.getPrecioTotal());
        
        productoRepository.findById(detallePedido.getIdProducto())
            .ifPresent(producto -> dto.setNombreProducto(producto.getNombreProducto()));

        return dto;
    }

    public Pedido toEntity(PedidoDTO dto) {
        if (dto == null) {
            return null;
        }

        Pedido pedido = new Pedido();
        pedido.setIdPedido(dto.getIdPedido());
        pedido.setUsuarioId(dto.getUsuarioId());
        pedido.setFechaPedido(dto.getFechaPedido());
        pedido.setEstado(dto.getEstado());
        pedido.setDetalles(dto.getDetalles().stream()
                .map(detalleDTO -> toEntity(detalleDTO, pedido))
                .collect(Collectors.toList()));
        return pedido;
    }

    public DetallePedido toEntity(DetallePedidoDTO dto, Pedido pedido) {
        if (dto == null) {
            return null;
        }

        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setIdDetalle(dto.getIdDetalle());
        detallePedido.setPedido(pedido);
        detallePedido.setIdProducto(dto.getIdProducto());
        detallePedido.setCantidad(dto.getCantidad());
        detallePedido.setPrecioTotal(dto.getPrecioTotal());

        return detallePedido;
    }
}