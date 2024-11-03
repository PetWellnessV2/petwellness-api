package com.petwellness.mapper;

import com.petwellness.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petwellness.dto.MascotaRegistroDTO;
import com.petwellness.dto.MascotaProfileDTO;
import com.petwellness.model.entity.Mascota;
import com.petwellness.model.entity.Customer;

@RequiredArgsConstructor
@Component
public class MascotaMapper {
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    // Convert MascotaRegistroDTO to Mascota entity
    public Mascota toEntity(MascotaRegistroDTO mascotaRegistroDTO) {
        Mascota mascota = modelMapper.map(mascotaRegistroDTO, Mascota.class);
        if (mascotaRegistroDTO.getUsuarioId() != null) {
            Customer usuario = customerRepository.findById(mascotaRegistroDTO.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            mascota.setCustomer(usuario);
        }
        return mascota;
    }

    // Convert Mascota entity to MascotaProfileDTO
    public MascotaProfileDTO toMascotaProfileDTO(Mascota mascota) {
        MascotaProfileDTO mascotaProfileDTO = modelMapper.map(mascota, MascotaProfileDTO.class);
        mascotaProfileDTO.setIdCustomer(mascota.getCustomer().getUserId());
        return mascotaProfileDTO;
    }
}
