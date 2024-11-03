package com.petwellness.repository;

import com.petwellness.model.entity.User;
import com.petwellness.model.entity.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {
}
