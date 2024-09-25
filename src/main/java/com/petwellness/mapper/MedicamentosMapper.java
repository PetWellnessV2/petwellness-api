package com.petwellness.mapper;

import com.petwellness.dto.MedicamentosDTO;
import com.petwellness.model.entity.Medicamentos;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MedicamentosMapper {
    private final ModelMapper modelMapper;
    public MedicamentosMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public MedicamentosDTO toDTO(Medicamentos medicamentos) {
        return modelMapper.map(medicamentos, MedicamentosDTO.class);
    }
    public Medicamentos toEntity(MedicamentosDTO medicamentosDTO) {
        return modelMapper.map(medicamentosDTO, Medicamentos.class);
    }
}