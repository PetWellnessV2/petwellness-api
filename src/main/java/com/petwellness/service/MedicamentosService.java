package com.petwellness.service;

import com.petwellness.dto.MedicamentosDTO;
import java.util.List;

public interface MedicamentosService {
    List<MedicamentosDTO> getAllMedicamentos();
    MedicamentosDTO getMedicamentoById(Integer id);
    MedicamentosDTO createMedicamento(MedicamentosDTO medicamentoDTO);
    MedicamentosDTO updateMedicamento(Integer id, MedicamentosDTO medicamentoDTO);
    void deleteMedicamento(Integer id);
}
