package com.petwellness.service;
import com.petwellness.model.entity.Notification;
import java.util.List;

public interface NotificationService {
    void enviarNotificacion(Integer usuarioId, String mensaje);
    List<Notification> obtenerNotificacionesDeUsuario(Integer usuarioId);
    Notification marcarComoLeida(Integer id);
    void eliminarNotificacion(Integer id);
}
