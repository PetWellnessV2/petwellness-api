package com.petwellness.repository;

import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.enums.RecordatorioStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordatorioRepository extends JpaRepository<Recordatorio, Integer> {

    List<Recordatorio> findByUsuario_UserId(Integer userId);
    List<Recordatorio> findByUsuario_UserIdAndRecordatorioStatus(Integer userId, RecordatorioStatus status);
}
