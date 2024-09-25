package com.petwellness.service;

import com.petwellness.model.entity.Producto;
import java.util.List;

public interface ProductoService {

    List<Producto> getAllProductos();
    Producto getProductoById(Integer idProducto);
    Producto createProducto(Producto producto);
    Producto updateProducto(Integer idProducto, Producto producto);
    void deleteProducto(Integer idProducto);
}
