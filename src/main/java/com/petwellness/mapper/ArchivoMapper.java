package com.petwellness.mapper;

import com.petwellness.dto.ArchivoProfileDTO;
import com.petwellness.dto.ArchivoRegistroDTO;
import com.petwellness.model.entity.Archivos;
import com.petwellness.model.entity.Mascota;
import com.petwellness.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ArchivoMapper {
    private final ModelMapper modelMapper;
    private final MascotaRepository mascotaRepository;

    public ArchivoProfileDTO toArchivoProfileDTO(Archivos archivos) {
        ArchivoProfileDTO archivoProfileDTO = modelMapper.map(archivos, ArchivoProfileDTO.class);
        archivoProfileDTO.setNomMascota(archivos.getMascota().getNombre());
        return archivoProfileDTO;
    }
    public Archivos toEntity(ArchivoRegistroDTO archivoRegistroDTO) {
        Archivos archivos = modelMapper.map(archivoRegistroDTO, Archivos.class);
        Mascota mascota = mascotaRepository.findById(archivos.getMascota().getIdMascota())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));
        archivos.setMascota(mascota);
        return archivos;
    }
}
