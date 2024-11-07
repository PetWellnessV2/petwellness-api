package com.petwellness.repository;

import com.petwellness.model.entity.Mascota;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    Optional<Mascota> findByNombreAndEspecieAndGeneroAndCustomer_UserId(
    String nombre, Especie especie, Genero genero, Integer usuarioId);
    List<Mascota> findByCustomer_UserId(Integer userId);
    Optional<Mascota> findByIdMascota(Integer idMascota);

    // Implementacion de busqueda de mascotas por filtros usando JPQL(Java Persistence Query Language)
    @Query("SELECT m FROM Mascota m WHERE " +
           "(:nombre IS NULL OR m.nombre LIKE %:nombre%) AND " +
           "(:especie IS NULL OR m.especie = :especie) AND " +
           "(:genero IS NULL OR m.genero = :genero)")
    List<Mascota> findAllWithFilters(@Param("nombre") String nombre,
                                     @Param("especie") Especie especie,
                                     @Param("genero") Genero genero);
}
