package com.petwellness.repository;

import com.petwellness.model.entity.Veterinario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {
    // metdo para no registrar el mismo veterinario
    Optional<Veterinario> findByNombreAndApellido(String nombre, String apellido);
    boolean existsByNombreAndApellido(String nombre, String apellido);
}
