package com.petwellness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petwellness.model.entity.*;

import java.util.Optional;

public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Integer> {
    Optional<CategoriaProducto> findByName(String nombreCategoria);

}
