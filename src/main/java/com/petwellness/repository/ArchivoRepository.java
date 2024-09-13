package com.petwellness.repository;

import com.petwellness.model.entity.Archivos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchivoRepository extends JpaRepository<Archivos, Integer> {
    List<Archivos> findByRegistroMascotaIdMascota(Integer idMascota);
}

