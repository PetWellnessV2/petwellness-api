package com.petwellness.service;
import com.petwellness.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    //void enviarNotificacion(Integer usuarioId, String mensaje);
    List<NotificationDTO> getAllNotificaciones();
    NotificationDTO getNotificationById(Integer id);
    NotificationDTO updateNotificacion(Integer id, NotificationDTO notificacionRegistroDTO);
    NotificationDTO createNotificacion(NotificationDTO notificacionRegistroDTO);
    void deleteNotificacion(Integer id);
    NotificationDTO markAsRead(Integer id);
    List<NotificationDTO> getNotificacionesByUsuarioId(Integer usuarioId);
}
