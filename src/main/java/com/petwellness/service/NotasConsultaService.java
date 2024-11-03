package com.petwellness.service;

import com.petwellness.dto.NotasConsultaDTO;

import java.util.List;

public interface NotasConsultaService {
    List<NotasConsultaDTO> findNotasConsultaByMascotaId(Integer mascotaId);
    List<NotasConsultaDTO> getAllNotasConsulta();
    NotasConsultaDTO getNotasConsultaById(Integer id);
    NotasConsultaDTO createNotasConsulta(NotasConsultaDTO notasConsulta);
    NotasConsultaDTO updateNotasConsulta(Integer id, NotasConsultaDTO notasConsulta);
    void deleteNotasConsulta(Integer id);
}
