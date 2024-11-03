package com.petwellness.repository;

import com.petwellness.model.entity.HorariosDisponibles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorariosDisponiblesRepository extends JpaRepository<HorariosDisponibles, Integer> {
    List<HorariosDisponibles> findByVeterinario_Vet_UserId(Integer userId);
}
