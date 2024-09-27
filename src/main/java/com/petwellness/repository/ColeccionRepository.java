package com.petwellness.repository;

import com.petwellness.model.entity.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColeccionRepository extends JpaRepository<Coleccion, Integer> {
    List<Coleccion> findByUsuarioUserId(Integer userId);
}