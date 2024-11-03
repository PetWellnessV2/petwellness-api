package com.petwellness.api;

import com.petwellness.dto.NotificationDTO;
import com.petwellness.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notificaciones")
@PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotificaciones() {
        List<NotificationDTO> notificationDTOS = notificationService.getAllNotificaciones();
        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Integer id) {
        NotificationDTO notificationDTO = notificationService.getNotificationById(id);
        return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificationDTO>> getNotificacionesByUsuarioId(@PathVariable Integer usuarioId) {
        List<NotificationDTO> notifications = notificationService.getNotificacionesByUsuarioId(usuarioId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<NotificationDTO> createNotificacion(@Valid @RequestBody NotificationDTO notificationRegistroDTO) {
        NotificationDTO newNotificacion = notificationService.createNotificacion(notificationRegistroDTO);
        return new ResponseEntity<>(newNotificacion, HttpStatus.CREATED);
    }

    // Marcar una notificación como leída
    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotificacion(
            @PathVariable Integer id,
            @Valid @RequestBody NotificationDTO notificationRegistroDTO) {
        NotificationDTO updateNotificacion = notificationService.updateNotificacion(id, notificationRegistroDTO);
        return new ResponseEntity<>(updateNotificacion, HttpStatus.OK);
    }

    @PutMapping("/leido/{id}")
    public ResponseEntity<NotificationDTO> markNotificationAsRead(@PathVariable Integer id) {
        NotificationDTO notificationDTO = notificationService.markAsRead(id);
        return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Integer id) {
        notificationService.deleteNotificacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

