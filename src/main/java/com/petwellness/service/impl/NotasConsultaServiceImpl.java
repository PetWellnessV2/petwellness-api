package com.petwellness.service.impl;

import com.petwellness.dto.NotasConsultaDTO;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.NotasConsultaMapper;
import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.NotasConsulta;
import com.petwellness.repository.NotasConsultaRepository;
import com.petwellness.service.NotasConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotasConsultaServiceImpl implements NotasConsultaService {

    private final NotasConsultaRepository notasConsultaRepository;
    private final NotasConsultaMapper notasConsultaMapper;

    @Override
    public List<NotasConsultaDTO> findNotasConsultaByMascotaId(Integer mascotaId) {
        List<NotasConsulta> notasConsultas = notasConsultaRepository.findByMascota_IdMascota(mascotaId);
        return notasConsultas.stream()
                .map(notasConsultaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotasConsultaDTO> getAllNotasConsulta() {
        return notasConsultaRepository.findAll().stream()
                .map(notasConsultaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public NotasConsultaDTO getNotasConsultaById(Integer id) {
        NotasConsulta notasConsulta = notasConsultaRepository.findById(id.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Nota de consulta no encontrada"));
        return notasConsultaMapper.toDTO(notasConsulta);
    }

    @Transactional
    @Override
    public NotasConsultaDTO createNotasConsulta(NotasConsultaDTO notasConsultaDTO) {
        NotasConsulta notasConsulta = notasConsultaMapper.toEntity(notasConsultaDTO);
        NotasConsulta savedNotasConsulta = notasConsultaRepository.save(notasConsulta);
        return notasConsultaMapper.toDTO(savedNotasConsulta);
    }

    @Transactional
    @Override
    public NotasConsultaDTO updateNotasConsulta(Integer id, NotasConsultaDTO notasConsultaDTO) {
        NotasConsulta existingNotasConsulta = notasConsultaRepository.findById(id.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Nota de consulta no encontrada"));

        existingNotasConsulta.setFecha(notasConsultaDTO.getFecha());
        existingNotasConsulta.setDescripcion(notasConsultaDTO.getDescripcion());

        NotasConsulta updatedNotasConsulta = notasConsultaRepository.save(existingNotasConsulta);
        return notasConsultaMapper.toDTO(updatedNotasConsulta);
    }

    @Transactional
    @Override
    public void deleteNotasConsulta(Integer id) {
        if (!notasConsultaRepository.existsById(id.longValue())) {
            throw new ResourceNotFoundException("Nota de consulta no encontrada");
        }
        notasConsultaRepository.deleteById(id.longValue());
    }
}
