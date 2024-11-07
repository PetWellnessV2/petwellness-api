package com.petwellness.service;

import com.petwellness.dto.ConsultaProfileDTO;
import com.petwellness.dto.ConsultaRegistroDTO;
import com.petwellness.model.entity.Consulta;
import com.petwellness.model.enums.EstadoConsulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.List;

public interface ConsultaService {
    List<ConsultaProfileDTO> findConsultasByMascotaId(Integer mascotaId);
    List<ConsultaProfileDTO> getAll();
    Page<ConsultaProfileDTO> paginate(Pageable pageable);
    ConsultaProfileDTO findById(Integer id);
    List<ConsultaProfileDTO> findByEstadoConsulta(EstadoConsulta estadoConsulta);
    ConsultaProfileDTO create(ConsultaRegistroDTO consultaRegistroDTO);
    ConsultaProfileDTO update(Integer id, ConsultaRegistroDTO consultaRegistroDTO);
    void delete(Integer id);
    ConsultaProfileDTO posponerConsulta(Integer consultaId, LocalTime nuevaHora, String nuevaFecha, Integer veterinarioUserId);
}
