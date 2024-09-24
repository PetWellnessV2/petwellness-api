package com.petwellness.repository;

import com.petwellness.model.entity.Archivos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivoRepository extends JpaRepository<Archivos, Integer> {
}
