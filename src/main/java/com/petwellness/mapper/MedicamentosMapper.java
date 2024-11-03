package com.petwellness.mapper;

import com.petwellness.dto.MedicamentosProfileDTO;
import com.petwellness.dto.MedicamentosRegistroDTO;
import com.petwellness.model.entity.Mascota;
import com.petwellness.model.entity.Medicamentos;
import com.petwellness.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MedicamentosMapper {
    private final ModelMapper modelMapper;
    private final MascotaRepository mascotaRepository;

    public MedicamentosProfileDTO toDTO(Medicamentos medicamentos) {
        MedicamentosProfileDTO medicamentosProfileDTO = modelMapper.map(medicamentos, MedicamentosProfileDTO.class);
        medicamentosProfileDTO.setNomMascota(medicamentos.getMascota().getNombre());
        return medicamentosProfileDTO;
    }

    public Medicamentos toEntity(MedicamentosRegistroDTO medicamentosRegistroDTO) {
        Medicamentos medicamentos = modelMapper.map(medicamentosRegistroDTO, Medicamentos.class);
        Mascota mascota = mascotaRepository.findById(medicamentosRegistroDTO.getIdMascota())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));
        medicamentos.setMascota(mascota);
        return medicamentos;
    }
}
