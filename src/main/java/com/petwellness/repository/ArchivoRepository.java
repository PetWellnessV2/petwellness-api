package com.petwellness.repository;

import com.petwellness.model.entity.Archivos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivos, Integer> {
    Optional<Archivos> findByNombre(String nombreArchivo);
}
