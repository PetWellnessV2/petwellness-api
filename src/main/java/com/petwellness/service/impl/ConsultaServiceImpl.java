package com.petwellness.service.impl;

import com.petwellness.model.entity.Consulta;
import com.petwellness.repository.ConsultaRepository;
import com.petwellness.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConsultaServiceImpl implements ConsultaService {
    private final ConsultaRepository consultaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Consulta> getAll() {
        return consultaRepository.findAll();
    }

    @Override
    public List<Consulta> getAllByMascotaId(Integer idMascota) {
        return consultaRepository.findByRegistroMascotaIdMascota(idMascota);
    }

    @Override
    public List<Consulta> getUpcomingConsultasByTipo(List<String> tiposConsulta) {
        return consultaRepository.findByTipoConsultaInAndFechaHoraAfter(
                tiposConsulta, LocalDateTime.now());
    }

    @Override
    public Page<Consulta> paginate(Pageable pageable) {
        return consultaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Consulta findById(Integer id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
    }

    @Transactional
    @Override
    public Consulta create(Consulta consulta) {
        consulta.setCreatedAt(LocalDateTime.now());
        return consultaRepository.save(consulta);
    }

    @Transactional
    @Override
    public Consulta update(Integer id, Consulta updateConsulta) {
        Consulta consultaFromDb = findById(id);

        if(consultaFromDb == null){
            throw new IllegalArgumentException("La Consulta con ID " + id + " no existe");
        }

        consultaFromDb.setRazonConsulta(updateConsulta.getRazonConsulta());
        consultaFromDb.setTipoConsulta(updateConsulta.getTipoConsulta());
        consultaFromDb.setUpdatedAt(LocalDateTime.now());
        return consultaRepository.save(consultaFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        if(!consultaRepository.existsById(id)) {
            throw new RuntimeException("La consulta no existe");
        }
        consultaRepository.deleteById(id);
    }
}
