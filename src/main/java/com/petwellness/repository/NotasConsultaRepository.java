package com.petwellness.repository;

import com.petwellness.model.entity.NotasConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotasConsultaRepository extends JpaRepository<NotasConsulta, Long> {
    List<NotasConsulta> findByMascota_IdMascota(Integer mascotaId);
}
