package com.petwellness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.ExamenFisico;
import com.petwellness.model.entity.ExamenesLaboratorio;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.entity.Veterinario;
import java.util.List;

@Repository
public interface UserVeterinarioRepository extends JpaRepository<Veterinario, Integer> {

    // Consultar mascotas asignadas a un veterinario
    @Query("SELECT r FROM RegistroMascota r WHERE r.user.id = :usuarioUserId")
    List<RegistroMascota> findRegistroMascotasByUserId(@Param("usuarioUserId") Integer usuarioUserId);
    
    // Consultar consultas de una mascota asignada a un veterinario
    @Query("SELECT c FROM Consulta c WHERE c.registroMascota.idMascota = :mascotaId AND c.registroMascota.user.id = :usuarioUserId")
    List<Consulta> findConsultasByMascotaAndUserId(@Param("mascotaId") Integer mascotaId, @Param("usuarioUserId") Integer usuarioUserId);
    
    // Consultar exámenes físicos de una mascota asignada a un veterinario
    @Query("SELECT e FROM ExamenFisico e WHERE e.registroMascota.idMascota = :mascotaId AND e.registroMascota.user.id = :usuarioUserId")
    List<ExamenFisico> findExamenesFisicosByMascotaAndUserId(@Param("mascotaId") Integer mascotaId, @Param("usuarioUserId") Integer usuarioUserId);
    
    // Consultar exámenes de laboratorio de una mascota asignada a un veterinario
    @Query("SELECT e FROM ExamenesLaboratorio e WHERE e.registroMascota.idMascota = :mascotaId AND e.registroMascota.user.id = :usuarioUserId")
    List<ExamenesLaboratorio> findExamenesLaboratorioByMascotaAndUserId(@Param("mascotaId") Integer mascotaId, @Param("usuarioUserId") Integer usuarioUserId);
}

