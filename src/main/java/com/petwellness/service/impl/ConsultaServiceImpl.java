package com.petwellness.service.impl;

import com.petwellness.dto.ConsultaDTO;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.ConsultaMapper;
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
    private final ConsultaMapper consultaMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ConsultaDTO> getAll() {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultas.stream()
                .map(consultaMapper::toDTO)
                .toList();
    }

    @Override
    public Page<ConsultaDTO> paginate(Pageable pageable) {
        return consultaRepository.findAll(pageable)
                .map(consultaMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public ConsultaDTO findById(Integer id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta no encontrada"));
        return consultaMapper.toDTO(consulta);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ConsultaDTO> findByEstadoConsulta(EstadoConsulta estadoConsulta) {
        List<Consulta> consultas = consultaRepository.findByEstadoConsulta(estadoConsulta);
        return consultas.stream()
                .map(consultaMapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public ConsultaDTO create(ConsultaDTO consultaDTO) {
        HorariosDisponibles horario = horariosDisponiblesRepository.findById(consultaDTO.getIdHorario())
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado con id: " + consultaDTO.getIdHorario()));

        RegistroMascota mascota = registroMascotaRepository.findById(consultaDTO.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + consultaDTO.getIdMascota()));

        Consulta consulta = consultaMapper.toEntity(consultaDTO);
        consulta.setHorariosDisponibles(horario);
        consulta.setRegistroMascota(mascota);
        consulta.setCreatedAt(LocalDateTime.now());
        consulta.setUpdatedAt(LocalDateTime.now());

        Consulta nuevaConsulta = consultaRepository.save(consulta);

        return consultaMapper.toDTO(nuevaConsulta);
    }

    @Transactional
    @Override
    public ConsultaDTO update(Integer id, ConsultaDTO consultaDTO) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta no encontrada"));

        if (consultaDTO.getIdHorario() != null) {
            HorariosDisponibles horario = horariosDisponiblesRepository.findById(consultaDTO.getIdHorario())
                    .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado con id: " + consultaDTO.getIdHorario()));
            consulta.setHorariosDisponibles(horario);
        }

        if (consultaDTO.getIdMascota() != null) {
            RegistroMascota mascota = registroMascotaRepository.findById(consultaDTO.getIdMascota())
                    .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + consultaDTO.getIdMascota()));
            consulta.setRegistroMascota(mascota);
        }

        consulta.setRazonConsulta(consultaDTO.getRazonConsulta());
        consulta.setEstadoConsulta(consultaDTO.getEstadoConsulta());
        consulta.setTipoConsulta(consultaDTO.getTipoConsulta());
        consulta.setUpdatedAt(LocalDateTime.now());

        Consulta consultaActualizada = consultaRepository.save(consulta);
        return consultaMapper.toDTO(consultaActualizada);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        if (!consultaRepository.existsById(id)) {
            throw new ResourceNotFoundException("La consulta no existe");
        }
        consultaRepository.deleteById(id);
    }
}
