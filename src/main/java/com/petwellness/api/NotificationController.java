package com.petwellness.api;

import com.petwellness.dto.NotificationDTO;
import com.petwellness.dto.NotificationRegistroDTO;
import com.petwellness.model.entity.Notification;
import com.petwellness.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notificaciones")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotificaciones() {
        List<NotificationDTO> notificationDTOs = notificationService.getAllNotificaciones();
        return new ResponseEntity<>(notificationDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Integer id) {
        NotificationDTO notificationDTO = notificationService.getNotificationById(id);
        return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificationDTO>> getNotificacionesByUsuarioId(@PathVariable Integer usuarioId) {
        List<NotificationDTO> notifications = notificationService.getNotificacionesByUserId(usuarioId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<NotificationRegistroDTO> createNotificacion(@Valid @RequestBody NotificationRegistroDTO notificationRegistroDTO) {
        NotificationRegistroDTO newNotificacion = notificationService.createNotificacion(notificationRegistroDTO);
        return new ResponseEntity<>(newNotificacion, HttpStatus.CREATED);
    }

    // Marcar una notificación como leída
    @PutMapping("/{id}")
    public ResponseEntity<NotificationRegistroDTO> updateNotificacion(
            @PathVariable Integer id,
            @Valid @RequestBody NotificationRegistroDTO notificationRegistroDTO) {
        NotificationRegistroDTO updateNotificacion = notificationService.updateNotificacion(id, notificationRegistroDTO);
        return new ResponseEntity<>(updateNotificacion, HttpStatus.OK);
    }

    @PutMapping("/leido/{id}")
    public ResponseEntity<NotificationRegistroDTO> markNotificationAsRead(@PathVariable Integer id) {
        NotificationRegistroDTO notificationDTO = notificationService.markAsRead(id);
        return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Integer id) {
        notificationService.deleteNotificacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

