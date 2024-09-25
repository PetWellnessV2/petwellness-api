package com.petwellness.service.impl;

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
    private final UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public void enviarNotificacion(Integer usuarioId, String mensaje) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Notification notification = new Notification();
        notification.setUsuario(usuario);
        notification.setMensaje(mensaje);
        notification.setLeida(false);  // Estado de no leída
        notification.setFechaCreacion(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Transactional
    @Override
    public List<Notification> obtenerNotificacionesDeUsuario(Integer usuarioId) {
        return notificationRepository.findByUsuario_UserId(usuarioId);
    }

    @Transactional
    @Override
    public Notification marcarComoLeida(Integer id) {
        Notification notificacion = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notificacion.setLeida(true);
        return notificationRepository.save(notificacion);  // Devuelve la notificación actualizada
    }


    @Transactional
    @Override
    public void eliminarNotificacion(Integer id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notificación no encontrada");
        }
        notificationRepository.deleteById(id);
    }
}