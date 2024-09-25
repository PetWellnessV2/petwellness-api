package com.petwellness.repository;

import com.petwellness.model.entity.Archivos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchivoRepository extends JpaRepository<Archivos, Integer> {
    Optional<Archivos> findByNombreArchivo(String nombreArchivo);
}
