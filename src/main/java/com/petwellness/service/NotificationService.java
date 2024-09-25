package com.petwellness.service;
import com.petwellness.dto.NotificationDTO;
import com.petwellness.dto.NotificationRegistroDTO;
import com.petwellness.model.entity.Notification;
import java.util.List;

public interface NotificationService {
    //void enviarNotificacion(Integer usuarioId, String mensaje);
    List<NotificationDTO> getAllNotificaciones();
    NotificationDTO getNotificationById(Integer id);
    NotificationRegistroDTO updateNotificacion(Integer id, NotificationRegistroDTO notificacionRegistroDTO);
    NotificationRegistroDTO createNotificacion(NotificationRegistroDTO notificacionRegistroDTO);
    void deleteNotificacion(Integer id);
}
