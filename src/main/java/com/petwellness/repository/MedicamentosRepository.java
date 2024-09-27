package com.petwellness.repository;

import com.petwellness.model.entity.Medicamentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicamentosRepository extends JpaRepository<Medicamentos, Integer> {
    Optional<Medicamentos> findByDescripcion(String descripcion);
}
