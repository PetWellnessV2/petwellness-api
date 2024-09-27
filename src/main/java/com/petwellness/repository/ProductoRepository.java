package com.petwellness.repository;

import com.petwellness.model.entity.Producto;
import com.petwellness.model.enums.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByTipoProductoIn(List<TipoProducto> tiposProducto);
}