package com.petwellness.mapper;

import com.petwellness.dto.NotasConsultaDTO;
import com.petwellness.model.entity.Mascota;
import com.petwellness.model.entity.NotasConsulta;
import com.petwellness.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotasConsultaMapper {
    private final ModelMapper modelMapper;
    private final MascotaRepository mascotaRepository;

    public NotasConsultaDTO toDTO(NotasConsulta notasConsulta) {
        NotasConsultaDTO notasConsultaDTO = modelMapper.map(notasConsulta, NotasConsultaDTO.class);
        notasConsultaDTO.setIdMascota(notasConsulta.getMascota().getIdMascota());
        return notasConsultaDTO;
    }
    public NotasConsulta toEntity(NotasConsultaDTO notasConsultaDTO) {
        NotasConsulta notasConsulta = modelMapper.map(notasConsultaDTO, NotasConsulta.class);
        Mascota mascota = mascotaRepository.findById(notasConsulta.getMascota().getIdMascota())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));
        notasConsulta.setMascota(mascota);
        return notasConsulta;
    }
}
