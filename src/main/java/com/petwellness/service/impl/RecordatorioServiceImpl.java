package com.petwellness.service.impl;

import com.petwellness.dto.RecordatorioDTO;
import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.entity.Usuario;
import com.petwellness.repository.RecordatorioRepository;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.service.RecordatorioService;
import com.petwellness.util.NotificacionUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@Validated
@RequiredArgsConstructor
public class RecordatorioServiceImpl implements RecordatorioService {

    private final RecordatorioRepository recordatorioRepository;
    private final UsuarioRepository usuarioRepository;
    // private final RegistroMascotaRepository registroMascotaRepository;
    private final NotificacionUtil notificacionUtil;

    @Override
    @Transactional
    public RecordatorioDTO crearRecordatorio(@Valid RecordatorioDTO recordatorioDTO) {
        validarCamposObligatorios(recordatorioDTO);

        Usuario usuario = usuarioRepository.findById(recordatorioDTO.getUsuarioId().intValue())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // RegistroMascota mascota = registroMascotaRepository.findById(recordatorioDTO.getMascotaId())
        //     .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        Recordatorio recordatorio = new Recordatorio();
        recordatorio.setUsuario(usuario);
        // recordatorio.setMascota(mascota);
        recordatorio.setTitulo(recordatorioDTO.getTitulo());
        recordatorio.setDescripcion(recordatorioDTO.getDescripcion());
        recordatorio.setFechaHora(recordatorioDTO.getFechaHora());

        Recordatorio savedRecordatorio = recordatorioRepository.save(recordatorio);
        notificacionUtil.programarNotificacion(savedRecordatorio);

        return convertToDTO(savedRecordatorio);
    }

    @Override
    @Transactional
    public void eliminarRecordatorio(Long id) {
        Recordatorio recordatorio = recordatorioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recordatorio no encontrado"));

        if (recordatorio.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se puede eliminar un recordatorio pasado");
        }

        recordatorioRepository.delete(recordatorio);
        notificacionUtil.notificarEliminacionRecordatorio(recordatorio);
    }

    private void validarCamposObligatorios(RecordatorioDTO recordatorioDTO) {
        if (recordatorioDTO.getUsuarioId() == null) {
            throw new IllegalArgumentException("El ID de usuario es obligatorio");
        }
        // if (recordatorioDTO.getMascotaId() == null) {
        //     throw new IllegalArgumentException("El ID de mascota es obligatorio");
        // }
        if (recordatorioDTO.getTitulo() == null || recordatorioDTO.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        if (recordatorioDTO.getFechaHora() == null) {
            throw new IllegalArgumentException("La fecha y hora son obligatorias");
        }
        if (recordatorioDTO.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha y hora deben ser futuras");
        }
    }

    private RecordatorioDTO convertToDTO(Recordatorio recordatorio) {
        RecordatorioDTO dto = new RecordatorioDTO();
        dto.setId(recordatorio.getId());
        dto.setUsuarioId(Long.valueOf(recordatorio.getUsuario().getUserId()));
        // dto.setMascotaId(recordatorio.getMascota().getIdMascota());
        dto.setTitulo(recordatorio.getTitulo());
        dto.setDescripcion(recordatorio.getDescripcion());
        dto.setFechaHora(recordatorio.getFechaHora());
        dto.setCompletado(recordatorio.isCompletado());
        return dto;
    }
}