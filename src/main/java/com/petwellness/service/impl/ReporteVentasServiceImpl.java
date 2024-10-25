package com.petwellness.service.impl;

import com.petwellness.dto.ProductoDTO;
import com.petwellness.dto.ReporteVentasDTO;
import com.petwellness.mapper.ReporteVentasMapper;
import com.petwellness.model.entity.DetallePedido;
import com.petwellness.repository.DetallePedidoRepository;
import com.petwellness.service.ProductoService;
import com.petwellness.service.ReporteVentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteVentasServiceImpl implements ReporteVentasService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ProductoService productoService;  // Este servicio es necesario para obtener los datos del producto

    @Autowired
    private ReporteVentasMapper reporteVentasMapper;

    @Override
    public List<ReporteVentasDTO> generarReporteDeVentas() {
        List<DetallePedido> detallesPedidos = detallePedidoRepository.findAll();

        return detallesPedidos.stream()
                .map(detallePedido -> {
                    ProductoDTO productoDTO = productoService.getProductoById(detallePedido.getIdProducto());
                    return reporteVentasMapper.toDTO(detallePedido, productoDTO);
                })
                .collect(Collectors.toList());
    }
}
