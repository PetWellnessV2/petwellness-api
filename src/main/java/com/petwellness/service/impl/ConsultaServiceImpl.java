package com.petwellness.service.impl;

import com.petwellness.dto.ConsultaProfileDTO;
import com.petwellness.dto.ConsultaRegistroDTO;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.exception.UnauthorizedException;
import com.petwellness.mapper.ConsultaMapper;
import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.HorariosDisponibles;
import com.petwellness.model.entity.Mascota;
import com.petwellness.model.enums.EstadoConsulta;
import com.petwellness.repository.ConsultaRepository;
import com.petwellness.repository.HorariosDisponiblesRepository;
import com.petwellness.repository.MascotaRepository;
import com.petwellness.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ConsultaServiceImpl implements ConsultaService {
    private final ConsultaRepository consultaRepository;
    private final HorariosDisponiblesRepository horariosDisponiblesRepository;
    private final MascotaRepository registroMascotaRepository;
    private final ConsultaMapper consultaMapper;

    @Transactional
    @Override
    public List<ConsultaProfileDTO> findConsultasByMascotaId(Integer mascotaId) {
        List<Consulta> consultas = consultaRepository.findByMascota_IdMascota(mascotaId);
        return consultas.stream()
                .map(consultaMapper::toConsultaProfileDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ConsultaProfileDTO> getAll() {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultas.stream()
                .map(consultaMapper::toConsultaProfileDTO)
                .toList();
    }

    @Override
    public Page<ConsultaProfileDTO> paginate(Pageable pageable) {
        return consultaRepository.findAll(pageable)
                .map(consultaMapper::toConsultaProfileDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public ConsultaProfileDTO findById(Integer id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta no encontrada"));
        return consultaMapper.toConsultaProfileDTO(consulta);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ConsultaProfileDTO> findByEstadoConsulta(EstadoConsulta estadoConsulta) {
        List<Consulta> consultas = consultaRepository.findByEstadoConsulta(estadoConsulta);
        return consultas.stream()
                .map(consultaMapper::toConsultaProfileDTO)
                .toList();
    }

    @Transactional
    @Override
    public ConsultaProfileDTO create(ConsultaRegistroDTO consultaRegistroDTO) {
        HorariosDisponibles horario = horariosDisponiblesRepository.findById(consultaRegistroDTO.getIdHorario())
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado con id: " + consultaRegistroDTO.getIdHorario()));

        Mascota mascota = registroMascotaRepository.findById(consultaRegistroDTO.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + consultaRegistroDTO.getIdMascota()));

        Consulta consulta = consultaMapper.toEntity(consultaRegistroDTO);
        consulta.setHorariosDisponibles(horario);
        consulta.setMascota(mascota);
        consulta.setCreatedAt(LocalDateTime.now());
        consulta.setUpdatedAt(LocalDateTime.now());

        Consulta nuevaConsulta = consultaRepository.save(consulta);

        return consultaMapper.toConsultaProfileDTO(nuevaConsulta);
    }

    @Transactional
    @Override
    public ConsultaProfileDTO update(Integer id, ConsultaRegistroDTO consultaRegistroDTO) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta no encontrada"));

        if (consultaRegistroDTO.getIdHorario() != null) {
            HorariosDisponibles horario = horariosDisponiblesRepository.findById(consultaRegistroDTO.getIdHorario())
                    .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado con id: " + consultaRegistroDTO.getIdHorario()));
            consulta.setHorariosDisponibles(horario);
        }

        if (consultaRegistroDTO.getIdMascota() != null) {
            Mascota mascota = registroMascotaRepository.findById(consultaRegistroDTO.getIdMascota())
                    .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + consultaRegistroDTO.getIdMascota()));
            consulta.setMascota(mascota);
        }

        consulta.setRazonConsulta(consultaRegistroDTO.getRazonConsulta());
        consulta.setEstadoConsulta(consultaRegistroDTO.getEstadoConsulta());
        consulta.setTipoConsulta(consultaRegistroDTO.getTipoConsulta());
        consulta.setUpdatedAt(LocalDateTime.now());

        Consulta consultaActualizada = consultaRepository.save(consulta);
        return consultaMapper.toConsultaProfileDTO(consultaActualizada);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        if (!consultaRepository.existsById(id)) {
            throw new ResourceNotFoundException("La consulta no existe");
        }
        consultaRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ConsultaProfileDTO posponerConsulta(Integer consultaId, LocalTime nuevaHora, String nuevaFecha, Integer veterinarioUserId) {
        // Obtener la consulta existente
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta no encontrada"));

        // Obtener el horario actual de la consulta
        HorariosDisponibles horarioActual = horariosDisponiblesRepository
                .findById(consulta.getHorariosDisponibles().getIdHorario())
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado"));

        // Verificar si el veterinario tiene acceso a la consulta
        Integer idVeterinario = horarioActual.getVeterinario().getUsuario_user_id();
        if (!idVeterinario.equals(veterinarioUserId)) {
            throw new UnauthorizedException("El veterinario no tiene acceso a esta consulta");
        }

        // Crear un nuevo horario disponible
        HorariosDisponibles nuevoHorario = new HorariosDisponibles();
        nuevoHorario.setVeterinario(horarioActual.getVeterinario()); // Asignar el mismo veterinario
        nuevoHorario.setHora(nuevaHora); // Suponiendo que has cambiado esto a Integer
        nuevoHorario.setFecha(LocalDate.parse(nuevaFecha)); // Asegúrate de que la fecha esté en el formato correcto

        // Guardar el nuevo horario utilizando el servicio
        //horariosDisponiblesService.create(nuevoHorario);

        // Actualizar la consulta para que use el nuevo horario
        consulta.setHorariosDisponibles(nuevoHorario);
        consultaRepository.save(consulta); // Guardar la consulta actualizada

        ConsultaProfileDTO consultaProfileDTO = consultaMapper.toConsultaProfileDTO(consulta);
        //consultaProfileDTO.setIdHorario(nuevoHorario.getIdHorario());
        //consultaProfileDTO.setIdMascota(consulta.getMascota().getIdMascota());

        return consultaProfileDTO;
    }
}
