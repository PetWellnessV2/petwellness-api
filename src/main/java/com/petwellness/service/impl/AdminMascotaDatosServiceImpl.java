package com.petwellness.service.impl;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.RegistroMascotaMapper;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;
import com.petwellness.repository.MascotaDatosRepository;
import com.petwellness.service.MascotaDatosService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class AdminMascotaDatosServiceImpl implements MascotaDatosService {

    private final MascotaDatosRepository mascotaDatosRepository;
    private final RegistroMascotaMapper registroMascotaMapper;

    @Override
    public List<RegistroMascotaDTO> getAll() {
        List<RegistroMascota> mascotas = mascotaDatosRepository.findAll();
        return mascotas.stream()
                .map(registroMascotaMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<RegistroMascotaDTO> paginate(Pageable pageable) {
        Page<RegistroMascota> mascotas = mascotaDatosRepository.findAll(pageable);
        return mascotas.map(registroMascotaMapper::toDTO);
    }

    @Transactional
    @Override
    public RegistroMascotaDTO create(RegistroMascotaDTO registroMascotaDTO) {
        mascotaDatosRepository.findByNombreAndEspecieAndGeneroAndUserId(
                registroMascotaDTO.getNombre(),
                registroMascotaDTO.getEspecie(),
                registroMascotaDTO.getGenero(),
                registroMascotaDTO.getClienteId()).ifPresent(mascota -> {
                    throw new BadRequestException("La mascota ya esta registrada.");
                });

        RegistroMascota mascota = registroMascotaMapper.toEntity(registroMascotaDTO);
        mascotaDatosRepository.save(mascota);

        return registroMascotaMapper.toDTO(mascota);
    }

    @Transactional(readOnly = true)
    @Override
    public RegistroMascotaDTO findById(int id) {
        RegistroMascota mascota = mascotaDatosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID " + id + " no existe."));

        return registroMascotaMapper.toDTO(mascota);
    }

    @Override
    @Transactional
    public RegistroMascotaDTO update(Integer id, RegistroMascotaDTO registroMascotaDTO) {
        RegistroMascota mascota = mascotaDatosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID " + id + " no existe."));

        mascotaDatosRepository.findByNombreAndEspecieAndGeneroAndUserId(
                registroMascotaDTO.getNombre(),
                registroMascotaDTO.getEspecie(),
                registroMascotaDTO.getGenero(),
                registroMascotaDTO.getClienteId()).filter(existingMascota -> !existingMascota.getIdMascota().equals(id))
                .ifPresent(existingMascota -> {
                    throw new BadRequestException("Ya existe esa mascota.");
                });

        mascota.setNombre(registroMascotaDTO.getNombre());
        mascota.setEspecie(registroMascotaDTO.getEspecie());
        mascota.setGenero(registroMascotaDTO.getGenero());
        mascota.setRaza(registroMascotaDTO.getRaza());
        mascota.setEdad(registroMascotaDTO.getEdad());
        mascota.setFoto(registroMascotaDTO.getFoto());
        mascota.setFechaNacimiento(registroMascotaDTO.getFechaNacimiento());
        mascota.setDescripcion(registroMascotaDTO.getDescripcion());
        mascota.setDireccion(registroMascotaDTO.getDireccion());
        mascota.setMiembroID(registroMascotaDTO.getMiembroID());
        mascota.setTitularPoliza(registroMascotaDTO.getTitularPoliza());
        mascota.setInfoAdicional(registroMascotaDTO.getInfoAdicional());

        mascotaDatosRepository.save(mascota);

        return registroMascotaMapper.toDTO(mascota);

    }

    @Transactional
    @Override
    public void delete(Integer id) {
        RegistroMascota registroMascota = mascotaDatosRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID " + id + " no existe."));
        mascotaDatosRepository.delete(registroMascota);
    }

    @Override
    public List<RegistroMascotaDTO> findWithFilters(String nombre, Especie especie, Genero genero) {
        List<RegistroMascota> mascotas = mascotaDatosRepository.findAllWithFilters(nombre, especie, genero);
        return mascotas.stream()
                .map(registroMascotaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
