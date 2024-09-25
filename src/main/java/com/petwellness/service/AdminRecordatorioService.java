package com.petwellness.service;

import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.enums.RecordatorioStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;
import com.petwellness.dto.RecordatorioDTO;

public interface AdminRecordatorioService {
    List<Recordatorio> getAll();


    List<Recordatorio> findByUsuarioId(Integer userId);
    List<Recordatorio> findByUsuarioIdAndStatus(Integer userId, RecordatorioStatus status);



    Page<Recordatorio> paginateByUsuarioId(Integer usuarioId, Pageable pageable);
    Page<Recordatorio> paginate(Pageable pageable);

    RecordatorioDTO createRecordatorio(RecordatorioDTO recordatorioDTO);
    RecordatorioDTO updateRecordatorio(Integer id, RecordatorioDTO recordatorioDTO);
    void deleteRecordatorio(Integer id);
    RecordatorioDTO getRecordatorioById(Integer id);
    List<RecordatorioDTO> getAllRecordatorios();






    Recordatorio findById(Integer userId);
    Recordatorio create(Recordatorio recordatorio);
    Recordatorio update(Integer userId, Recordatorio recordatorio);
    void delete(Integer userId);
}
