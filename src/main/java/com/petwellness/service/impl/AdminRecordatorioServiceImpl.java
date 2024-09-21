package com.petwellness.service.impl;

import com.petwellness.model.entity.Recordatorio;
import com.petwellness.repository.RecordatorioRepository;
import com.petwellness.service.AdminRecordatorioService;
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

    @Transactional(readOnly = true)
    @Override
    public List<Recordatorio> getAll() {
        return recordatorioRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Recordatorio> paginate(Pageable pageable) {
        return recordatorioRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Recordatorio findById(Integer Id) {
        return recordatorioRepository.findById(Id).
                orElseThrow(()-> new RuntimeException("Recordatorio no encontrado"));
    }

    @Transactional
    @Override
    public Recordatorio create(Recordatorio recordatorio) {
        recordatorio.setFechaHora(LocalDateTime.now());
        return recordatorioRepository.save(recordatorio);
    }

    @Transactional
    @Override
    public Recordatorio update(Integer id, Recordatorio updatedRecordatorio) {
        Recordatorio recordatorioFromDb = findById(id);
        recordatorioFromDb.setTitulo(updatedRecordatorio.getTitulo());
        recordatorioFromDb.setDescripcion(updatedRecordatorio.getDescripcion());
        recordatorioFromDb.setFechaHora(updatedRecordatorio.getFechaHora());
        return recordatorioRepository.save(recordatorioFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Recordatorio recordatorio = findById(id);
        recordatorioRepository.delete(recordatorio);
    }
}
