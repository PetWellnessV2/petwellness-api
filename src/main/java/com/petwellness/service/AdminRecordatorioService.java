package com.petwellness.service;

import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.enums.RecordatorioStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminRecordatorioService {
    List<Recordatorio> getAll();


    List<Recordatorio> findByUsuarioId(Integer userId);
    List<Recordatorio> findByUsuarioIdAndStatus(Integer userId, RecordatorioStatus status);




    Page<Recordatorio> paginate(Pageable pageable);
    Recordatorio findById(Integer userId);
    Recordatorio create(Recordatorio recordatorio);
    Recordatorio update(Integer userId, Recordatorio recordatorio);
    void delete(Integer userId);
}
