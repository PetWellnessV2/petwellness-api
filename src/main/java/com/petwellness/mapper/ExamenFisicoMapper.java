package com.petwellness.mapper;

import com.petwellness.dto.ExamenFisicoDTO;
import com.petwellness.model.entity.ExamenFisico;
import com.petwellness.model.entity.Mascota;
import com.petwellness.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExamenFisicoMapper {
    private final ModelMapper modelMapper;
    private final MascotaRepository registroMascotaRepository;

    public ExamenFisicoDTO toDTO(ExamenFisico examenFisico) {
        ExamenFisicoDTO dto = modelMapper.map(examenFisico, ExamenFisicoDTO.class);
        if (examenFisico.getMascota() != null) {
            dto.setIdMascota(examenFisico.getMascota().getIdMascota());
        }
        return dto;
    }

    public ExamenFisico toEntity(ExamenFisicoDTO dto) {
        ExamenFisico examenFisico = modelMapper.map(dto, ExamenFisico.class);
        if (dto.getIdMascota() != null) {
            Mascota mascota = registroMascotaRepository.findById(dto.getIdMascota())
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + dto.getIdMascota()));
            examenFisico.setMascota(mascota);
        }
        return examenFisico;
    }
}
