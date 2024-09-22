package com.petwellness.util;

import com.petwellness.model.entity.Recordatorio;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class NotificacionUtil {

    public void programarNotificacion(Recordatorio recordatorio) {
        LocalDateTime now = LocalDateTime.now();
        if (recordatorio.getFechaHora().isAfter(now)) {
            System.out.println("Notificación programada para el recordatorio: " + recordatorio.getTitulo() +
                               " en la fecha: " + recordatorio.getFechaHora());
        } else {
            System.out.println("No se puede programar notificación para una fecha pasada: " + recordatorio.getTitulo());
        }
    }

    public void notificarEliminacionRecordatorio(Recordatorio recordatorio) {
        System.out.println("Notificación: El recordatorio '" + recordatorio.getTitulo() + 
                           "' ha sido eliminado. Estaba programado para: " + recordatorio.getFechaHora());
    }
}