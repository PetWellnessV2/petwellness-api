package com.petwellness.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petwellness.model.entity.Albergue;


public interface AlbergueRepository extends JpaRepository<Albergue, Integer> {
    // Metodo para no registrar un albergue con el mismo ruc
    boolean existsByRuc(String ruc);
    Optional<Albergue> findByRuc(String ruc);
}
