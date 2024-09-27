package com.petwellness.service;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.ExamenFisico;
import com.petwellness.model.entity.ExamenesLaboratorio;
import java.util.List;

public interface UserVeterinarioService {
    List<RegistroMascotaDTO> findRegistroMascotasByUsuario_user_id(Integer usuario_user_id);
    List<Consulta> findConsultasByMascotaAndUsuarioUserId(Integer mascotaId, Integer usuarioUserId);
    List<ExamenFisico> findExamenesFisicosByMascotaAndUsuarioUserId(Integer mascotaId, Integer usuarioUserId);
    List<ExamenesLaboratorio> findExamenesLaboratorioByMascotaAndUsuarioUserId(Integer mascotaId, Integer usuarioUserId);
    Consulta posponerConsulta(Integer consultaId, Integer nuevaHora, String nuevaFecha, Integer veterinarioUserId);
}

