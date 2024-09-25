package com.petwellness.api;

import com.petwellness.dto.NotificationDTO;
import com.petwellness.model.entity.Notification;
import com.petwellness.service.NotificationService;
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

    // Obtener todas las notificaciones de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificationDTO>> getNotificacionesDeUsuario(@PathVariable Integer usuarioId) {
        List<Notification> notificaciones = notificationService.obtenerNotificacionesDeUsuario(usuarioId);

        // Convertir la lista de entidades Notification a NotificationDTO
        List<NotificationDTO> notificacionesDTO = notificaciones.stream()
                .map(notificacion -> {
                    NotificationDTO dto = new NotificationDTO();
                    dto.setUsuarioId(notificacion.getUsuario().getUserId());  // Asignar el ID del usuario
                    dto.setMensaje(notificacion.getMensaje());
                    dto.setLeida(notificacion.isLeida());
                    dto.setFechaCreacion(notificacion.getFechaCreacion());
                    return dto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(notificacionesDTO, HttpStatus.OK);
    }


    // Enviar una nueva notificación a un usuario
    @PostMapping
    public ResponseEntity<NotificationDTO> crearNotificacion(@RequestBody NotificationDTO notificationDTO) {
        notificationService.enviarNotificacion(notificationDTO.getUsuarioId(), notificationDTO.getMensaje());
        return new ResponseEntity<>(notificationDTO, HttpStatus.CREATED);
    }

    // Marcar una notificación como leída
    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> marcarNotificacionComoLeida(@PathVariable Integer id) {
        Notification notificacion = notificationService.marcarComoLeida(id);

        // Convertir la notificación actualizada en un DTO
        NotificationDTO dto = new NotificationDTO();
        dto.setUsuarioId(notificacion.getUsuario().getUserId());
        dto.setMensaje(notificacion.getMensaje());
        dto.setLeida(notificacion.isLeida());
        dto.setFechaCreacion(notificacion.getFechaCreacion());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Integer id) {
        notificationService.eliminarNotificacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Retorna un estado 204
    }
}

