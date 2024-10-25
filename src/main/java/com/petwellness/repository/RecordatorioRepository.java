package com.petwellness.repository;

import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.enums.RecordatorioStatus;
import com.petwellness.model.enums.TipoRecordatorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordatorioRepository extends JpaRepository<Recordatorio, Integer> {

    List<Recordatorio> findByUserId(Integer userId);
    List<Recordatorio> findByUserIdAndRecordatorioStatus(Integer userId, RecordatorioStatus status);
    Page<Recordatorio> findByUserId(Integer userId, Pageable pageable);
    boolean existsByUserIdAndMascota_IdMascotaAndFechaHoraAndTipoRecordatorio(
            Integer userId, Integer mascotaId, LocalDateTime fechaHora, TipoRecordatorio tipoRecordatorio);
    Optional<Recordatorio> findByRecordatorioIdAndUserId(Integer recordatorioId, Integer usuarioId);
}
