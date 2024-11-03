package com.petwellness.mapper;

import com.petwellness.dto.HorariosDisponiblesDTO;
import com.petwellness.model.entity.HorariosDisponibles;
import com.petwellness.model.entity.Veterinario;
import com.petwellness.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HorariosDisponiblesMapper {

    private final ModelMapper modelMapper;
    private final VeterinarioRepository veterinarioRepository;

    public HorariosDisponiblesDTO toDTO(HorariosDisponibles horariosDisponibles) {
        HorariosDisponiblesDTO horariosDisponiblesDTO = modelMapper.map(horariosDisponibles, HorariosDisponiblesDTO.class);
        horariosDisponiblesDTO.setVeterinarioId(horariosDisponibles.getVeterinario().getUsuario_user_id());
        return horariosDisponiblesDTO;
    }

    public HorariosDisponibles toEntity(HorariosDisponiblesDTO horariosDisponiblesDTO) {
        HorariosDisponibles horariosDisponibles = modelMapper.map(horariosDisponiblesDTO, HorariosDisponibles.class);

        Veterinario veterinario = veterinarioRepository.findById(horariosDisponiblesDTO.getVeterinarioId())
                .orElseThrow(() -> new IllegalArgumentException("Veterinario no encontrado"));
        horariosDisponibles.setVeterinario(veterinario);

        return horariosDisponibles;
    }
}
