package com.petwellness.service;

import com.petwellness.dto.MedicamentosProfileDTO;
import com.petwellness.dto.MedicamentosRegistroDTO;

import java.util.List;

public interface MedicamentosService {
    List<MedicamentosProfileDTO> getAllMedicamentos();
    MedicamentosProfileDTO getMedicamentoById(Integer id);
    MedicamentosProfileDTO createMedicamento(MedicamentosRegistroDTO medicamentosRegistroDTO);
    MedicamentosProfileDTO updateMedicamento(Integer id, MedicamentosRegistroDTO medicamentosRegistroDTO);
    void deleteMedicamento(Integer id);
}
