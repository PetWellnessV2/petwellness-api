package com.petwellness.repository;

import com.petwellness.model.entity.EventoSalud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoSaludRepository extends JpaRepository<EventoSalud, Integer> {
    List<EventoSalud> findByFechaEventoBetween(LocalDate startDate, LocalDate endDate);
}
