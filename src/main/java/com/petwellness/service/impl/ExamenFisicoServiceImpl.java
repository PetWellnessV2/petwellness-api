package com.petwellness.service.impl;

import com.petwellness.dto.ExamenFisicoDTO;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.ExamenFisicoMapper;
import com.petwellness.model.entity.ExamenFisico;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.repository.ExamenFisicoRepository;
import com.petwellness.repository.MascotaDatosRepository;
import com.petwellness.service.ExamenFisicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamenFisicoServiceImpl implements ExamenFisicoService {

    private final ExamenFisicoRepository examenFisicoRepository;
    private final MascotaDatosRepository registroMascotaRepository;
    private final ExamenFisicoMapper examenFisicoMapper;

    @Transactional
    @Override
    public ExamenFisicoDTO crearExamenFisico(ExamenFisicoDTO examenFisicoDTO) {
        RegistroMascota mascota = registroMascotaRepository.findById(examenFisicoDTO.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + examenFisicoDTO.getIdMascota()));

        ExamenFisico examenFisico = examenFisicoMapper.toEntity(examenFisicoDTO);
        examenFisico.setRegistroMascota(mascota);

        ExamenFisico nuevoExamenFisico = examenFisicoRepository.save(examenFisico);

        return examenFisicoMapper.toDTO(nuevoExamenFisico);
    }

    @Transactional
    @Override
    public ExamenFisicoDTO actualizarExamenFisico(Integer id, ExamenFisicoDTO examenFisicoDTO) {
        ExamenFisico examenFisicoExistente = examenFisicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El examen físico con ID " + id + " no existe"));

        RegistroMascota mascota = registroMascotaRepository.findById(examenFisicoDTO.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + examenFisicoDTO.getIdMascota()));

        examenFisicoExistente.setRegistroMascota(mascota);
        examenFisicoExistente.setPresionArterial(examenFisicoDTO.getPresionArterial());
        examenFisicoExistente.setPulso(examenFisicoDTO.getPulso());
        examenFisicoExistente.setTemperatura(examenFisicoDTO.getTemperatura());
        examenFisicoExistente.setPeso(examenFisicoDTO.getPeso());
        examenFisicoExistente.setAltura(examenFisicoDTO.getAltura());

        ExamenFisico examenFisicoActualizado = examenFisicoRepository.save(examenFisicoExistente);

        return examenFisicoMapper.toDTO(examenFisicoActualizado);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamenFisicoDTO> obtenerExamenesFisicos() {
        List<ExamenFisico> examenes = examenFisicoRepository.findAll();
        return examenes.stream().map(examenFisicoMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ExamenFisicoDTO obtenerExamenFisicoPorId(Integer id) {
        ExamenFisico examenFisico = examenFisicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El examen físico con ID " + id + " no existe"));
        return examenFisicoMapper.toDTO(examenFisico);
    }

    @Transactional
    @Override
    public void eliminarExamenFisico(Integer id) {
        if (!examenFisicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("El examen físico no existe con id: " + id);
        }
        examenFisicoRepository.deleteById(id);
    }
}
