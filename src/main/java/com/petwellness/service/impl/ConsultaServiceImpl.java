package com.petwellness.service.impl;

import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.HorariosDisponibles;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.enums.EstadoConsulta;
import com.petwellness.repository.ConsultaRepository;
import com.petwellness.repository.HorariosDisponiblesRepository;
import com.petwellness.repository.MascotaDatosRepository;
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
    private final HorariosDisponiblesRepository horariosDisponiblesRepository;
    private final MascotaDatosRepository registroMascotaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Consulta> getAll() {
        return consultaRepository.findAll();
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


    @Transactional(readOnly = true)
    @Override
    public List<Consulta> findByEstadoConsulta(EstadoConsulta estadoConsulta) {
        return consultaRepository.findByEstadoConsulta(estadoConsulta);
    }

    @Transactional
    @Override
    public Consulta create(Consulta consulta) {
        HorariosDisponibles horario = horariosDisponiblesRepository.findById(consulta.getHorariosDisponibles().getIdHorario())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + consulta.getHorariosDisponibles().getIdHorario()));

        RegistroMascota mascota = registroMascotaRepository.findById(consulta.getRegistroMascota().getIdMascota())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + consulta.getRegistroMascota().getIdMascota()));

        consulta.setHorariosDisponibles(horario);
        consulta.setRegistroMascota(mascota);

        if (consulta.getEstadoConsulta() == null) {
            consulta.setEstadoConsulta(EstadoConsulta.PENDIENTE);
        }

        consulta.setCreatedAt(LocalDateTime.now());
        consulta.setUpdatedAt(LocalDateTime.now());

        return consultaRepository.save(consulta);
    }

    @Transactional
    @Override
    public Consulta update(Integer id, Consulta updateConsulta) {
        Consulta consultaFromDb = findById(id);

        if(consultaFromDb == null){
            throw new IllegalArgumentException("La Consulta con ID " + id + " no existe");
        }

        if (updateConsulta.getRazonConsulta() != null) {
            consultaFromDb.setRazonConsulta(updateConsulta.getRazonConsulta());
        }

        if (updateConsulta.getTipoConsulta() != null) {
            consultaFromDb.setTipoConsulta(updateConsulta.getTipoConsulta());
        }

        if (updateConsulta.getEstadoConsulta() != null) {
            consultaFromDb.setEstadoConsulta(updateConsulta.getEstadoConsulta());
        }

        if (updateConsulta.getHorariosDisponibles() != null) {
            HorariosDisponibles horario = horariosDisponiblesRepository.findById(updateConsulta.getHorariosDisponibles().getIdHorario())
                    .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + updateConsulta.getHorariosDisponibles().getIdHorario()));
            consultaFromDb.setHorariosDisponibles(horario);
        }

        if (updateConsulta.getRegistroMascota() != null) {
            RegistroMascota mascota = registroMascotaRepository.findById(updateConsulta.getRegistroMascota().getIdMascota())
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + updateConsulta.getRegistroMascota().getIdMascota()));
            consultaFromDb.setRegistroMascota(mascota);
        }

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
