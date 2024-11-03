package com.petwellness.repository;

import com.petwellness.model.entity.ProductoColeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoColeccionRepository extends JpaRepository<ProductoColeccion, Integer> {
    void deleteByColeccionIdAndProductoIdProducto(Integer coleccionId, Integer idProducto);

    boolean existsByColeccionIdAndProductoIdProducto(Integer coleccionId, Integer idProducto);

    @Query("SELECT pc.producto.idProducto FROM ProductoColeccion pc WHERE pc.coleccion.id = :coleccionId")
    List<Integer> findProductIdsByColeccionId(Integer coleccionId);
    void deleteByColeccionId(Integer coleccionId);
}