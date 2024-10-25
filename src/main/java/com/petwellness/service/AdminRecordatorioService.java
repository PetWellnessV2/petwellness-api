package com.petwellness.service;

import com.petwellness.dto.RecordatorioDTO;
import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.enums.RecordatorioStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminRecordatorioService {
    List<Recordatorio> getAll();
    List<Recordatorio> findByUserId(Integer userId);
    List<Recordatorio> findByUserIdAndStatus(Integer userId, RecordatorioStatus status);
    Page<Recordatorio> paginateByUserId(Integer usuarioId, Pageable pageable);
    Page<Recordatorio> paginate(Pageable pageable);
    RecordatorioDTO getRecordatorioById(Integer id);
    RecordatorioDTO createRecordatorio(RecordatorioDTO recordatorioDTO);
    RecordatorioDTO updateRecordatorio(Integer id, RecordatorioDTO recordatorioDTO);
    void deleteRecordatoriosByUserId(Integer usuarioId);
    void deleteRecordatorioByIdAndUserId(Integer recordatorioId, Integer usuarioId);
    void deleteRecordatorioById(Integer recordatorioId);

}