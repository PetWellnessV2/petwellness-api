package com.petwellness.mapper;

import com.petwellness.dto.MedicamentosRegistroDTO;
import com.petwellness.model.entity.Medicamentos;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MedicamentosRegistroMapper {
    private final ModelMapper modelMapper;
    public MedicamentosRegistroMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public MedicamentosRegistroDTO toDTO(Medicamentos medicamentos) {
        MedicamentosRegistroDTO medicamentosRegistroDTO = modelMapper.map(medicamentos, MedicamentosRegistroDTO.class);
        medicamentosRegistroDTO.setIdRegistroMascota(medicamentos.getRegistroMascota().getIdMascota());
        return medicamentosRegistroDTO;
    }

    public Medicamentos toEntity(MedicamentosRegistroDTO medicamentosRegistroDTO) {
        return modelMapper.map(medicamentosRegistroDTO, Medicamentos.class);
    }
}
