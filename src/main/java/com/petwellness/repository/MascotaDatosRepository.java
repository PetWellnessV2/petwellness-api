package com.petwellness.repository;

import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MascotaDatosRepository extends JpaRepository<RegistroMascota, Integer> {
    Optional<RegistroMascota> findByNombreAndEspecieAndGeneroAndUsuario_UserId(
    String nombre, Especie especie, Genero genero, Integer usuarioId);

}
