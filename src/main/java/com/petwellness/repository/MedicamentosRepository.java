package com.petwellness.repository;

import com.petwellness.model.entity.Medicamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicamentosRepository extends JpaRepository<Medicamentos, Integer> {
    List<Medicamentos> findByRegistroMascotaIdMascota(Integer idMascota);
}
