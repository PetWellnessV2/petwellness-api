package com.petwellness.repository;

import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.Mascota;
import com.petwellness.model.enums.EstadoConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByEstadoConsulta(EstadoConsulta estadoConsulta);
    List<Consulta> findByMascota_IdMascota(Integer mascotaId);
}
