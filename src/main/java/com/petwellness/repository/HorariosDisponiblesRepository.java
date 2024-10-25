package com.petwellness.repository;

import com.petwellness.model.entity.HorariosDisponibles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorariosDisponiblesRepository extends JpaRepository<HorariosDisponibles, Integer> {
    List<HorariosDisponibles> findByUserId(Integer userId);
}
