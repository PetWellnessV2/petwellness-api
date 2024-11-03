package com.petwellness.repository;

import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.ExamenFisico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenFisicoRepository extends JpaRepository<ExamenFisico, Integer> {
    List<ExamenFisico> findByMascota_IdMascota(Integer mascotaId);
}
