package com.petwellness.repository;

import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.enums.RecordatorioStatus;
import com.petwellness.model.enums.TipoRecordatorio;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordatorioRepository extends JpaRepository<Recordatorio, Integer> {

    List<Recordatorio> findByUsuario_UserId(Integer userId);
    List<Recordatorio> findByUsuario_UserIdAndRecordatorioStatus(Integer userId, RecordatorioStatus status);
    Page<Recordatorio> findByUsuario_UserId(Integer userId, Pageable pageable);
    boolean existsByUsuario_UserIdAndMascota_IdMascotaAndFechaHoraAndTipoRecordatorio(
            Integer userId, Integer mascotaId, LocalDateTime fechaHora, TipoRecordatorio tipoRecordatorio);
    Optional<Recordatorio> findByRecordatorioIdAndUsuario_UserId(Integer recordatorioId, Integer usuarioId);
}
