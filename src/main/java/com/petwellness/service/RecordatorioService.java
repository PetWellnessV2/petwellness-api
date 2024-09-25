package com.petwellness.service;

import com.petwellness.model.entity.Recordatorio;
import java.util.List;

public interface RecordatorioService {
    List<Recordatorio> getAll();
    Recordatorio findById(Integer id);
    Recordatorio create(Recordatorio recordatorio);
    Recordatorio update(Integer id, Recordatorio recordatorio);
    void delete(Integer id);
    void generarRecordatoriosAutomaticos();
}
