package com.petwellness.service;

import com.petwellness.dto.MedicamentosDTO;
import com.petwellness.dto.MedicamentosRegistroDTO;

import java.util.List;

public interface MedicamentosService {
    List<MedicamentosDTO> getAllMedicamentos();
    MedicamentosDTO getMedicamentoById(Integer id);
    MedicamentosRegistroDTO createMedicamento(MedicamentosRegistroDTO medicamentosRegistroDTO);
    MedicamentosRegistroDTO updateMedicamento(Integer id, MedicamentosRegistroDTO medicamentosRegistroDTO);
    void deleteMedicamento(Integer id);
}
