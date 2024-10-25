package com.petwellness.service.impl;

import com.petwellness.dto.RecordatorioDTO;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.enums.RecordatorioStatus;
import com.petwellness.repository.RecordatorioRepository;
import com.petwellness.service.AdminRecordatorioService;
import com.petwellness.mapper.RecordatorioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminRecordatorioServiceImpl implements AdminRecordatorioService {

    private final RecordatorioRepository recordatorioRepository;
    private final RecordatorioMapper recordatorioMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Recordatorio> getAll() {
        return recordatorioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recordatorio> findByUserId(Integer userId) {
        return recordatorioRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recordatorio> findByUserIdAndStatus(Integer userId, RecordatorioStatus status) {
        return recordatorioRepository.findByUserIdAndRecordatorioStatus(userId, status);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recordatorio> paginateByUserId(Integer usuarioId, Pageable pageable) {
        return recordatorioRepository.findByUserId(usuarioId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recordatorio> paginate(Pageable pageable) {
        return recordatorioRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public RecordatorioDTO getRecordatorioById(Integer id) {
        Recordatorio recordatorio = recordatorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recordatorio no encontrado"));
        return recordatorioMapper.toDTO(recordatorio);
    }

    @Override
    @Transactional
    public RecordatorioDTO createRecordatorio(RecordatorioDTO recordatorioDTO) {
        Recordatorio recordatorio = recordatorioMapper.toEntity(recordatorioDTO);
        recordatorio.setFechaHora(LocalDateTime.now());
        recordatorio.setRecordatorioStatus(RecordatorioStatus.CREADO);
        recordatorio = recordatorioRepository.save(recordatorio);
        return recordatorioMapper.toDTO(recordatorio);
    }

    @Override
    @Transactional
    public RecordatorioDTO updateRecordatorio(Integer id, RecordatorioDTO recordatorioDTO) {
        Recordatorio recordatorio = recordatorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recordatorio no encontrado"));
        recordatorioMapper.updateEntityFromDTO(recordatorio, recordatorioDTO);
        recordatorio = recordatorioRepository.save(recordatorio);
        return recordatorioMapper.toDTO(recordatorio);
    }

    @Override
    @Transactional
    public void deleteRecordatoriosByUserId(Integer usuarioId) {
        List<Recordatorio> recordatorios = recordatorioRepository.findByUserId(usuarioId);
        recordatorioRepository.deleteAll(recordatorios);
    }

    @Override
    @Transactional
    public void deleteRecordatorioByIdAndUserId(Integer recordatorioId, Integer usuarioId) {
        Recordatorio recordatorio = recordatorioRepository.findByRecordatorioIdAndUserId(recordatorioId, usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Recordatorio no encontrado para el id " + recordatorioId + " y usuario id " + usuarioId));
        
        recordatorioRepository.delete(recordatorio);
    }

    @Override
    @Transactional
    public void deleteRecordatorioById(Integer recordatorioId) {
        Recordatorio recordatorio = recordatorioRepository.findById(recordatorioId)
            .orElseThrow(() -> new ResourceNotFoundException("Recordatorio no encontrado para el id " + recordatorioId));
        
        recordatorioRepository.delete(recordatorio);
    }
}