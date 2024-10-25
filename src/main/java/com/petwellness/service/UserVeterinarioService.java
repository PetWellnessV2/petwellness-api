package com.petwellness.service;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.ExamenFisico;
import com.petwellness.model.entity.ExamenesLaboratorio;
import java.util.List;

public interface UserVeterinarioService {
    List<RegistroMascotaDTO> findRegistroMascotasByUserId(Integer usuario_user_id);
    List<Consulta> findConsultasByMascotaAndUserId(Integer mascotaId, Integer usuarioUserId);
    List<ExamenFisico> findExamenesFisicosByMascotaAndUserId(Integer mascotaId, Integer usuarioUserId);
    List<ExamenesLaboratorio> findExamenesLaboratorioByMascotaAndUserId(Integer mascotaId, Integer usuarioUserId);
    Consulta posponerConsulta(Integer consultaId, Integer nuevaHora, String nuevaFecha, Integer veterinarioUserId);
}

