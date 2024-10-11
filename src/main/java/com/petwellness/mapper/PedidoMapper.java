package com.petwellness.mapper;

import com.petwellness.dto.DetallePedidoDTO;
import com.petwellness.dto.PedidoDTO;
import com.petwellness.model.entity.DetallePedido;
import com.petwellness.model.entity.Pedido;
import com.petwellness.repository.ProductoRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    private final ProductoRepository productoRepository;

    public PedidoMapper(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
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
        dto.setDetalles(pedido.getDetalles().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));
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
        return dto;
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

        BigDecimal precioTotal = productoRepository.findById(dto.getIdProducto())
                .map(producto -> producto.getCosto().multiply(BigDecimal.valueOf(dto.getCantidad())))
                .orElse(BigDecimal.ZERO);
        detallePedido.setPrecioTotal(precioTotal);

        return detallePedido;
    }
}