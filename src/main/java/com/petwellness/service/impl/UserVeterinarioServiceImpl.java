package com.petwellness.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.exception.UnauthorizedException;
import com.petwellness.mapper.RegistroMascotaMapper;
import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.ExamenFisico;
import com.petwellness.model.entity.ExamenesLaboratorio;
import com.petwellness.model.entity.HorariosDisponibles;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.repository.ConsultaRepository;
import com.petwellness.repository.HorariosDisponiblesRepository;
import com.petwellness.repository.UserVeterinarioRepository;
import com.petwellness.service.UserVeterinarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserVeterinarioServiceImpl implements UserVeterinarioService {

    private final UserVeterinarioRepository veterinarioRepository;
    private final RegistroMascotaMapper registroMascotaMapper;
    private final ConsultaRepository consultaRepository;
    private final HorariosDisponiblesRepository horariosDisponiblesRepository;
    private final HorariosDisponiblesServiceImpl horariosDisponiblesService;

    @Override
    public List<RegistroMascotaDTO> findRegistroMascotasByUsuario_user_id(Integer usuario_user_id) {
        List<RegistroMascota> mascotas = veterinarioRepository.findRegistroMascotasByUsuario_user_id(usuario_user_id);
        return mascotas.stream()
                .map(registroMascotaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Consulta> findConsultasByMascotaAndUsuarioUserId(Integer mascotaId, Integer usuarioUserId) {
        return veterinarioRepository.findConsultasByMascotaAndUsuarioUserId(mascotaId, usuarioUserId);
    }

    @Override
    public List<ExamenFisico> findExamenesFisicosByMascotaAndUsuarioUserId(Integer mascotaId, Integer usuarioUserId) {
        return veterinarioRepository.findExamenesFisicosByMascotaAndUsuarioUserId(mascotaId, usuarioUserId);
    }

    @Override
    public List<ExamenesLaboratorio> findExamenesLaboratorioByMascotaAndUsuarioUserId(Integer mascotaId,
            Integer usuarioUserId) {
        return veterinarioRepository.findExamenesLaboratorioByMascotaAndUsuarioUserId(mascotaId, usuarioUserId);
    }

    @Override
    public Consulta posponerConsulta(Integer consultaId, Integer nuevaHora, String nuevaFecha, Integer veterinarioUserId) {
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
        horariosDisponiblesService.create(nuevoHorario);

        // Actualizar la consulta para que use el nuevo horario
        consulta.setHorariosDisponibles(nuevoHorario);
        consultaRepository.save(consulta); // Guardar la consulta actualizada

        return consulta; 
    }

}
