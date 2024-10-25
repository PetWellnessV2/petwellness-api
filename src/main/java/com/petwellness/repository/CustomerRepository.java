package com.petwellness.repository;

import com.petwellness.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByNombreAndApellido(String nombre, String apellido);
    Optional<Cliente> findByNombreAndApellido(String nombre, String apellido);
    boolean existsByNombreAndApellidoAndUserIdNot(String nombre, String apellido, Integer userId);
}
