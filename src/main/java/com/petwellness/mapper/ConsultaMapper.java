package com.petwellness.mapper;

import com.petwellness.dto.ConsultaDTO;
import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.HorariosDisponibles;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.repository.HorariosDisponiblesRepository;
import com.petwellness.repository.MascotaDatosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ConsultaMapper {

    private final ModelMapper modelMapper;
    private final HorariosDisponiblesRepository horariosDisponiblesRepository;
    private final MascotaDatosRepository mascotaDatosRepository;

    public ConsultaMapper(ModelMapper modelMapper,
                          HorariosDisponiblesRepository horariosDisponiblesRepository,
                          MascotaDatosRepository mascotaDatosRepository) {
        this.modelMapper = modelMapper;
        this.horariosDisponiblesRepository = horariosDisponiblesRepository;
        this.mascotaDatosRepository = mascotaDatosRepository;
    }

    // Mapea de Entidad a DTO
    public ConsultaDTO toDTO(Consulta consulta) {
        ConsultaDTO consultaDTO = modelMapper.map(consulta, ConsultaDTO.class);

        // Asignar IDs de Horario y Mascota
        if (consulta.getHorariosDisponibles() != null) {
            consultaDTO.setIdHorario(consulta.getHorariosDisponibles().getIdHorario());
        }

        if (consulta.getRegistroMascota() != null) {
            consultaDTO.setIdMascota(consulta.getRegistroMascota().getIdMascota());
        }

        return consultaDTO;
    }

    public Consulta toEntity(ConsultaDTO consultaDTO) {
        Consulta consulta = modelMapper.map(consultaDTO, Consulta.class);

        if (consultaDTO.getIdHorario() != null) {
            HorariosDisponibles horario = horariosDisponiblesRepository.findById(consultaDTO.getIdHorario())
                    .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + consultaDTO.getIdHorario()));
            consulta.setHorariosDisponibles(horario);
        }

        if (consultaDTO.getIdMascota() != null) {
            RegistroMascota mascota = mascotaDatosRepository.findById(consultaDTO.getIdMascota())
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + consultaDTO.getIdMascota()));
            consulta.setRegistroMascota(mascota);
        }

        return consulta;
    }
}
