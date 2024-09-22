package com.petwellness.repository;

import com.petwellness.model.entity.RegistroMascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MascotaDatosRepository extends JpaRepository<RegistroMascota, Integer> {
    Page<RegistroMascota> findAll(Pageable pageable); // Paginación con Pageable
}
