package com.petwellness.service;

import com.petwellness.dto.RecordatorioDTO;

public interface RecordatorioService {
    RecordatorioDTO crearRecordatorio(RecordatorioDTO recordatorioDTO);

    void eliminarRecordatorio(Long id);
}