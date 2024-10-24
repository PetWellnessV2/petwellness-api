package com.petwellness.repository;

import com.petwellness.model.entity.Customer;
import com.petwellness.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByNombreAndApellido(String nombre, String apellido);
    Optional<Customer> findByNombreAndApellido(String nombre, String apellido);
    boolean existsByNombreAndApellidoAndUserIdNot(String nombre, String apellido, Integer userId);
}
