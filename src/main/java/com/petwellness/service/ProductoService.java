package com.petwellness.service;

import com.petwellness.dto.ProductoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {
    List<ProductoDTO> getAllProductos();
    Page<ProductoDTO> getAllProductosPaginated(Pageable pageable);
    ProductoDTO getProductoById(Integer id);
    ProductoDTO createProducto(ProductoDTO productoDTO);
    ProductoDTO updateProducto(Integer id, ProductoDTO productoDTO);
    void deleteProducto(Integer id);
}