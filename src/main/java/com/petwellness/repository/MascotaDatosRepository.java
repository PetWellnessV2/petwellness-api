package com.petwellness.repository;

import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MascotaDatosRepository extends JpaRepository<RegistroMascota, Integer> {
    Optional<RegistroMascota> findByNombreAndEspecieAndGeneroAndUserId(
    String nombre, Especie especie, Genero genero, Integer usuarioId);

    // Implementacion de busqueda de mascotas por filtros usando JPQL(Java Persistence Query Language)
    @Query("SELECT m FROM RegistroMascota m WHERE " +
           "(:nombre IS NULL OR m.nombre LIKE %:nombre%) AND " +
           "(:especie IS NULL OR m.especie = :especie) AND " +
           "(:genero IS NULL OR m.genero = :genero)")
    List<RegistroMascota> findAllWithFilters(@Param("nombre") String nombre,
                                              @Param("especie") Especie especie,
                                              @Param("genero") Genero genero);
}
