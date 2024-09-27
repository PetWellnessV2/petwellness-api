package com.petwellness.mapper;

import com.petwellness.dto.ExamenFisicoDTO;
import com.petwellness.model.entity.ExamenFisico;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.repository.MascotaDatosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ExamenFisicoMapper {
    private final ModelMapper modelMapper;
    private final MascotaDatosRepository registroMascotaRepository;

    public ExamenFisicoMapper(ModelMapper modelMapper, MascotaDatosRepository registroMascotaRepository) {
        this.modelMapper = modelMapper;
        this.registroMascotaRepository = registroMascotaRepository;
    }

    public ExamenFisicoDTO toDTO(ExamenFisico examenFisico) {
        ExamenFisicoDTO dto = modelMapper.map(examenFisico, ExamenFisicoDTO.class);
        if (examenFisico.getRegistroMascota() != null) {
            dto.setIdMascota(examenFisico.getRegistroMascota().getIdMascota());
        }
        return dto;
    }

    public ExamenFisico toEntity(ExamenFisicoDTO dto) {
        ExamenFisico examenFisico = modelMapper.map(dto, ExamenFisico.class);
        if (dto.getIdMascota() != null) {
            RegistroMascota mascota = registroMascotaRepository.findById(dto.getIdMascota())
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + dto.getIdMascota()));
            examenFisico.setRegistroMascota(mascota);
        }
        return examenFisico;
    }
}
