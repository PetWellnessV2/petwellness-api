package com.petwellness.repository;

import com.petwellness.model.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByRegistroMascotaIdMascota(Integer idMascota);
    List<Consulta> findByTipoConsultaInAndFechaHoraAfter(
            List<String> tiposConsulta, LocalDateTime fechaHora);
}


