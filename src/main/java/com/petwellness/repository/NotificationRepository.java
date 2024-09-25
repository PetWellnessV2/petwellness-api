package com.petwellness.repository;

import com.petwellness.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUsuario_UserId(Integer usuarioId);  // Obtener las notificaciones de un usuario
}
