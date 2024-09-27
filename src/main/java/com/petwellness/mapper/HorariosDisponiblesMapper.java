package com.petwellness.mapper;

import com.petwellness.dto.HorariosDisponiblesDTO;
import com.petwellness.model.entity.HorariosDisponibles;
import com.petwellness.model.entity.Veterinario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HorariosDisponiblesMapper {

    private final ModelMapper modelMapper;

    public HorariosDisponiblesMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public HorariosDisponiblesDTO toDTO(HorariosDisponibles horariosDisponibles) {
        HorariosDisponiblesDTO dto = new HorariosDisponiblesDTO();
        dto.setIdHorario(horariosDisponibles.getIdHorario());
        dto.setHora(horariosDisponibles.getHora());
        dto.setFecha(horariosDisponibles.getFecha());
        dto.setVeterinarioId(horariosDisponibles.getVeterinario().getUsuario_user_id());
        return dto;
    }

    public HorariosDisponibles toEntity(HorariosDisponiblesDTO horariosDisponiblesDTO) {
        HorariosDisponibles horario = new HorariosDisponibles();
        horario.setIdHorario(horariosDisponiblesDTO.getIdHorario());
        horario.setHora(horariosDisponiblesDTO.getHora());
        horario.setFecha(horariosDisponiblesDTO.getFecha());

        Veterinario veterinario = new Veterinario();
        veterinario.setUsuario_user_id(horariosDisponiblesDTO.getVeterinarioId());
        horario.setVeterinario(veterinario);

        return horario;
    }
}
