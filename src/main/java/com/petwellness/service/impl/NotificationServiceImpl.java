package com.petwellness.service.impl;

import com.petwellness.dto.NotificationDTO;
import com.petwellness.dto.NotificationRegistroDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.NotificationRegistroMapper;
import com.petwellness.model.entity.Notification;
import com.petwellness.model.entity.Usuario;
import com.petwellness.repository.NotificationRepository;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationRegistroMapper notificationRegistroMapper;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public List<NotificationDTO> getAllNotificaciones() {
        List<Notification> notificaciones = notificationRepository.findAll();
        return notificaciones.stream().map(notificacion -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setId(notificacion.getIdNotificacion());
            notificationDTO.setUsuarioId(notificacion.getUsuario().getUserId());
            notificationDTO.setMensaje(notificacion.getMensaje());
            notificationDTO.setLeida(notificacion.isLeida());
            return notificationDTO;
        }).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public NotificationDTO getNotificationById(Integer id) {
        Notification notificacion = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La notificación con ID "+id+" no existe"));
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notificacion.getIdNotificacion());
        notificationDTO.setUsuarioId(notificacion.getUsuario().getUserId());
        notificationDTO.setMensaje(notificacion.getMensaje());
        notificationDTO.setLeida(notificacion.isLeida());
        return notificationDTO;
    }

    @Transactional
    @Override
    public NotificationRegistroDTO updateNotificacion(Integer id, NotificationRegistroDTO notificacionRegistroDTO) {
        Notification notificacionFromDB = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La notificación con ID "+id+" no existe"));
        notificationRepository.findByMensaje(notificacionRegistroDTO.getMensaje())
                .filter(existingnotificacion -> !existingnotificacion.getIdNotificacion().equals(id))
                .ifPresent(existingnotificacion -> {
                    throw new BadRequestException("Ya existe una notificación con la misma descripción");
                });
        Integer idUsuario = notificacionRegistroDTO.getUsuarioId();
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID "+idUsuario+" no existe"));
        notificacionFromDB.setMensaje(notificacionRegistroDTO.getMensaje());
        notificacionFromDB.setUsuario(usuario);
        notificacionFromDB.setLeida(notificacionRegistroDTO.isLeida());
        notificacionFromDB.setFechaCreacion(LocalDateTime.now());
        notificacionFromDB = notificationRepository.save(notificacionFromDB);
        return notificationRegistroMapper.toDTO(notificacionFromDB);
    }

    @Transactional
    @Override
    public NotificationRegistroDTO createNotificacion(NotificationRegistroDTO notificacionRegistroDTO) {
        notificationRepository.findByMensaje(notificacionRegistroDTO.getMensaje())
                .ifPresent(existingnotificacion -> {
                    throw new BadRequestException("Ya existe una notificación con la misma descripción");
                });
        Integer idUsuario = notificacionRegistroDTO.getUsuarioId();
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID "+idUsuario+" no existe"));
        Notification notificacion = notificationRegistroMapper.toEntity(notificacionRegistroDTO);
        notificacion.setMensaje(notificacionRegistroDTO.getMensaje());
        notificacion.setUsuario(usuario);
        notificacion.setLeida(notificacionRegistroDTO.isLeida());
        notificacion.setFechaCreacion(LocalDateTime.now());
        notificacion = notificationRepository.save(notificacion);
        return notificationRegistroMapper.toDTO(notificacion);
    }

    @Transactional
    @Override
    public void deleteNotificacion(Integer id) {
        Notification notificacion = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La notificaición con ID "+id+" no existe"));
        notificationRepository.delete(notificacion);
    }
}