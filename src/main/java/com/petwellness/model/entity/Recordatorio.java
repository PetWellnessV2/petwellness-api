package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recordatorio")
@IdClass(RecordatorioPK.class)
public class Recordatorio {
    @Id
    @Column(name = "id_recordatorio")
    private Integer recordatorioId;

    @Id
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "fk_recordatorio_usuario"))
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "mascota_id", referencedColumnName = "id_mascota",
            foreignKey = @ForeignKey(name = "fk_recordatorio_mascota"))
    private RegistroMascota mascota;

    @Column(name = "tipo_recordatorio", nullable = false)
    private String tipoRecordatorio;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "completado", nullable = false)
    private Boolean completado;


}