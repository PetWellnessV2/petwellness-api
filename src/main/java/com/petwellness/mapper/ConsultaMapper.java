package com.petwellness.mapper;

import com.petwellness.dto.ConsultaProfileDTO;
import com.petwellness.dto.ConsultaRegistroDTO;
import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.HorariosDisponibles;
import com.petwellness.model.entity.Mascota;
import com.petwellness.repository.HorariosDisponiblesRepository;
import com.petwellness.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ConsultaMapper {

    private final ModelMapper modelMapper;
    private final HorariosDisponiblesRepository horariosDisponiblesRepository;
    private final MascotaRepository mascotaRepository;

    // Mapea de Entidad a DTO
    public ConsultaProfileDTO toConsultaProfileDTO(Consulta consulta) {
        ConsultaProfileDTO consultaDTO = modelMapper.map(consulta, ConsultaProfileDTO.class);
        // Asignar IDs de Horario y Mascota
        if (consulta.getHorariosDisponibles() != null) {
            consultaDTO.setIdHorario(consulta.getHorariosDisponibles().getIdHorario());
        }
        if (consulta.getMascota() != null) {
            consultaDTO.setIdMascota(consulta.getMascota().getIdMascota());
        }
        return consultaDTO;
    }

    public Consulta toEntity(ConsultaRegistroDTO consultaRegistroDTO) {
        Consulta consulta = modelMapper.map(consultaRegistroDTO, Consulta.class);

        if (consultaRegistroDTO.getIdHorario() != null) {
            HorariosDisponibles horario = horariosDisponiblesRepository.findById(consultaRegistroDTO.getIdHorario())
                    .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + consultaRegistroDTO.getIdHorario()));
            consulta.setHorariosDisponibles(horario);
        }

        if (consultaRegistroDTO.getIdMascota() != null) {
            Mascota mascota = mascotaRepository.findById(consultaRegistroDTO.getIdMascota())
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + consultaRegistroDTO.getIdMascota()));
            consulta.setMascota(mascota);
        }

        return consulta;
    }
}
