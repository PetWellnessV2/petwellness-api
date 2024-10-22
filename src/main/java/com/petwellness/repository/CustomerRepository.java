package com.petwellness.repository;

import com.petwellness.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByNombreAndApellido(String nombre, String apellido);

    boolean existsByNombreAndApellidoAndUserIdNot(String nombre, String apellido, Integer userId);
}
