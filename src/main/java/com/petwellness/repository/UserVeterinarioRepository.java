package com.petwellness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.ExamenFisico;
import com.petwellness.model.entity.ExamenesLaboratorio;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.entity.Veterinario;
import java.util.List;


public interface UserVeterinarioRepository extends JpaRepository<Veterinario, Integer> {

            
    // Consultar mascotas asignadas a un veterinario
    @Query("SELECT r FROM RegistroMascota r WHERE r.usuario.userId = :usuarioUserId")
    List<RegistroMascota> findRegistroMascotasByUsuario_user_id(@Param("usuarioUserId") Integer usuarioUserId);
    
    // Consultar consultas de una mascota asignada a un veterinario
    @Query("SELECT c FROM Consulta c WHERE c.registroMascota.idMascota = :mascotaId AND c.registroMascota.usuario.userId = :usuarioUserId")
    List<Consulta> findConsultasByMascotaAndUsuarioUserId(@Param("mascotaId") Integer mascotaId, @Param("usuarioUserId") Integer usuarioUserId);
    
    // Consultar exámenes físicos de una mascota asignada a un veterinario
    @Query("SELECT e FROM ExamenFisico e WHERE e.registroMascota.idMascota = :mascotaId AND e.registroMascota.usuario.userId = :usuarioUserId")
    List<ExamenFisico> findExamenesFisicosByMascotaAndUsuarioUserId(@Param("mascotaId") Integer mascotaId, @Param("usuarioUserId") Integer usuarioUserId);
    
    // Consultar exámenes de laboratorio de una mascota asignada a un veterinario
    @Query("SELECT e FROM ExamenesLaboratorio e WHERE e.registroMascota.idMascota = :mascotaId AND e.registroMascota.usuario.userId = :usuarioUserId")
    List<ExamenesLaboratorio> findExamenesLaboratorioByMascotaAndUsuarioUserId(@Param("mascotaId") Integer mascotaId, @Param("usuarioUserId") Integer usuarioUserId);
}
