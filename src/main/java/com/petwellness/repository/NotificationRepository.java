package com.petwellness.repository;

import com.petwellness.model.entity.Medicamentos;
import com.petwellness.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Optional<Notification> findByMensaje(String mensaje);
    List<Notification> findByUsuarioUserId(Integer usuarioId);
}
