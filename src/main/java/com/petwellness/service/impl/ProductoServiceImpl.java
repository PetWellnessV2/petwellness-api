package com.petwellness.service.impl;

import com.petwellness.model.entity.Producto;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProductoById(Integer idProducto) {
        return productoRepository.findById(idProducto).orElse(null);
    }

    @Override
    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProducto(Integer idProducto, Producto producto) {
        Producto existingProducto = productoRepository.findById(idProducto).orElse(null);
        if (existingProducto == null) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + idProducto);
        }
        existingProducto.setNombreProducto(producto.getNombreProducto());
        existingProducto.setImagen(producto.getImagen());
        existingProducto.setDescripcion(producto.getDescripcion());
        existingProducto.setCosto(producto.getCosto());
        existingProducto.setTipoProducto(producto.getTipoProducto());
        existingProducto.setStock(producto.getStock());
        return productoRepository.save(existingProducto);
    }

    @Override
    public void deleteProducto(Integer idProducto) {
        productoRepository.deleteById(idProducto);
    }
}
