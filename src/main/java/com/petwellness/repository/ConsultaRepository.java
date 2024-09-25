package com.petwellness.repository;

import com.petwellness.model.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByMascotaIdConsulta(Integer idMascota);
}


