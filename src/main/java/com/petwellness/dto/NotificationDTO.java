package com.petwellness.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Integer usuarioId;        // Relación con el usuario (ID)
    private String mensaje;           // El mensaje de la notificación
    private boolean leida;            // Si la notificación ha sido leída
    private LocalDateTime fechaCreacion;  // Fecha de creación de la notificación
}
