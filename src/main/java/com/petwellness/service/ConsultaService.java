package com.petwellness.service;

import com.petwellness.dto.ConsultaDTO;
import com.petwellness.model.enums.EstadoConsulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConsultaService {

    List<ConsultaDTO> getAll();
    Page<ConsultaDTO> paginate(Pageable pageable);
    ConsultaDTO findById(Integer id);
    List<ConsultaDTO> findByEstadoConsulta(EstadoConsulta estadoConsulta);
    ConsultaDTO create(ConsultaDTO consultaDTO);
    ConsultaDTO update(Integer id, ConsultaDTO consultaDTO);
    void delete(Integer id);
}
