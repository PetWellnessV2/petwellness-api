package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notificaciones")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;

    @ManyToOne
    @JoinColumn(name = "usuario_user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "FK_notificacion_usuario"), nullable = false)
    private Usuario usuario;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "leida")
    private boolean leida;  // Estado: si la notificación ha sido leída o no

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;  // Fecha y hora en que se creó la notificación
}
