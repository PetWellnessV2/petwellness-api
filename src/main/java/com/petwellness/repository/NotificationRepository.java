package com.petwellness.repository;

import com.petwellness.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Optional<Notification> findByMensaje(String mensaje);
    List<Notification> findByUserId(Integer usuarioId);
}
