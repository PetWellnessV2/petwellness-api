package com.petwellness.service;

import com.petwellness.model.entity.Recordatorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminRecordatorioService {
    List<Recordatorio> getAll();
    Page<Recordatorio> paginate(Pageable pageable);
    Recordatorio findById(Integer Id);
    Recordatorio create(Recordatorio recordatorio);
    Recordatorio update(Integer id, Recordatorio recordatorio);
    void delete(Integer id);
}
