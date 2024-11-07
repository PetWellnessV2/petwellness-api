package com.petwellness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.petwellness.model.entity.Albergue;
import com.petwellness.model.entity.Mascota;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;

@Repository
public interface AlbergueRepository extends JpaRepository<Albergue, Integer> {
    /*
    // Consultar mascotas asignadas a un albergue
    @Query("SELECT r FROM Mascota r WHERE r.usuario.userId = :usuarioUserId")
    List<Mascota> findRegistroMascotasByUsuario_user_id(@Param("usuarioUserId") Integer usuarioUserId);

    // Listar mascotas de un albergue por filtro
    @Query("SELECT r FROM Mascota r WHERE r.usuario.userId = :usuarioUserId AND (:nombre IS NULL OR r.nombre LIKE %:nombre%) AND (:especie IS NULL OR r.especie = :especie) AND (:genero IS NULL OR r.genero = :genero)")
    List<Mascota> findAllWithFilters(
            @Param("usuarioUserId") Integer usuarioId,
            @Param("nombre") String nombre,
            @Param("especie") Especie especie,
            @Param("genero") Genero genero);
    */
}
