package com.petwellness.repository;

import com.petwellness.model.entity.Medicamentos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentosRepository extends JpaRepository<Medicamentos, Integer> {
}
