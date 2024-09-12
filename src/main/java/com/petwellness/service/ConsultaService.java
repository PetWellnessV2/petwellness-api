package com.petwellness.service;

import com.petwellness.model.entity.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConsultaService {
    List<Consulta> getAll();
    Page<Consulta> paginate(Pageable pageable);
    Consulta findById(Integer id);
    Consulta create(Consulta consulta);
    Consulta update(Integer id, Consulta updateConsulta);
    void delete(Integer id);

}
