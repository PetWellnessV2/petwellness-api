package com.petwellness.service.impl;

import com.petwellness.dto.MascotaProfileDTO;
import com.petwellness.dto.MascotaRegistroDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.MascotaMapper;
import com.petwellness.model.entity.Mascota;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;
import com.petwellness.repository.MascotaRepository;
import com.petwellness.repository.VeterinarioRepository;
import com.petwellness.service.MascotasService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class MascotasServiceImpl implements MascotasService {

    private final MascotaRepository mascotaRepository;
    private final MascotaMapper mascotaMapper;

    @Override
    public List<MascotaProfileDTO> findMascotasByUser_id(Integer usuario_user_id) {
        List<Mascota> mascotas = mascotaRepository.findByCustomer_UserId(usuario_user_id);
        return mascotas.stream()
                .map(mascotaMapper::toMascotaProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MascotaProfileDTO> getAll() {
        List<Mascota> mascotas = mascotaRepository.findAll();
        return mascotas.stream()
                .map(mascotaMapper::toMascotaProfileDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<MascotaProfileDTO> paginate(Pageable pageable) {
        Page<Mascota> mascotas = mascotaRepository.findAll(pageable);
        return mascotas.map(mascotaMapper::toMascotaProfileDTO);
    }

    @Transactional
    @Override
    public MascotaProfileDTO create(MascotaRegistroDTO mascotaRegistroDTO) {
        mascotaRepository.findByNombreAndEspecieAndGeneroAndCustomer_UserId(
                mascotaRegistroDTO.getNombre(),
                mascotaRegistroDTO.getEspecie(),
                mascotaRegistroDTO.getGenero(),
                mascotaRegistroDTO.getUsuarioId()).ifPresent(mascota -> {
                    throw new BadRequestException("La mascota ya esta registrada.");
                });

        Mascota mascota = mascotaMapper.toEntity(mascotaRegistroDTO);
        mascotaRepository.save(mascota);

        return mascotaMapper.toMascotaProfileDTO(mascota);
    }

    @Transactional(readOnly = true)
    @Override
    public MascotaProfileDTO findById(int id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID " + id + " no existe."));

        return mascotaMapper.toMascotaProfileDTO(mascota);
    }

    @Override
    @Transactional
    public MascotaProfileDTO update(Integer id, MascotaRegistroDTO mascotaRegistroDTO) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID " + id + " no existe."));

        mascotaRepository.findByNombreAndEspecieAndGeneroAndCustomer_UserId(
                mascotaRegistroDTO.getNombre(),
                mascotaRegistroDTO.getEspecie(),
                mascotaRegistroDTO.getGenero(),
                mascotaRegistroDTO.getUsuarioId()).filter(existingMascota -> !existingMascota.getIdMascota().equals(id))
                .ifPresent(existingMascota -> {
                    throw new BadRequestException("Ya existe esa mascota.");
                });

        mascota.setNombre(mascotaRegistroDTO.getNombre());
        mascota.setEspecie(mascotaRegistroDTO.getEspecie());
        mascota.setGenero(mascotaRegistroDTO.getGenero());
        mascota.setRaza(mascotaRegistroDTO.getRaza());
        mascota.setEdad(mascotaRegistroDTO.getEdad());
        mascota.setFoto(mascotaRegistroDTO.getFoto());
        mascota.setFechaNacimiento(mascotaRegistroDTO.getFechaNacimiento());
        mascota.setDescripcion(mascotaRegistroDTO.getDescripcion());
        mascota.setDireccion(mascotaRegistroDTO.getDireccion());
        mascota.setMiembroID(mascotaRegistroDTO.getMiembroID());
        mascota.setTitularPoliza(mascotaRegistroDTO.getTitularPoliza());
        mascota.setInfoAdicional(mascotaRegistroDTO.getInfoAdicional());

        mascotaRepository.save(mascota);

        return mascotaMapper.toMascotaProfileDTO(mascota);

    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Mascota mascota = mascotaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID " + id + " no existe."));
        mascotaRepository.delete(mascota);
    }

    @Override
    public List<MascotaProfileDTO> findWithFilters(String nombre, Especie especie, Genero genero) {
        List<Mascota> mascotas = mascotaRepository.findAllWithFilters(nombre, especie, genero);
        return mascotas.stream()
                .map(mascotaMapper::toMascotaProfileDTO)
                .collect(Collectors.toList());
    }
}
