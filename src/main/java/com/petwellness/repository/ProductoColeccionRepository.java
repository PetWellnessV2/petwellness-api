package com.petwellness.repository;

import com.petwellness.model.entity.ProductoColeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoColeccionRepository extends JpaRepository<ProductoColeccion, Integer> {
    void deleteByColeccionIdAndProductoIdProducto(Integer coleccionId, Integer idProducto);
}