package com.petwellness.repository;

import com.petwellness.model.entity.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {
    Optional<Veterinario> findByNombre(String nombre);
}
