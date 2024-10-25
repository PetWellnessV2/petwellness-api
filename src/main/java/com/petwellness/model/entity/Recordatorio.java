package com.petwellness.model.entity;

import com.petwellness.model.enums.RecordatorioStatus;
import com.petwellness.model.enums.TipoRecordatorio;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recordatorio")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recordatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recordatorio_id")
    private Integer recordatorioId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_recordatorio_user"))
    @EqualsAndHashCode.Include
    private User user;

    @ManyToOne
    @JoinColumn(name = "mascota_id", referencedColumnName = "id_mascota",
            foreignKey = @ForeignKey(name = "fk_recordatorio_mascota"))
    @EqualsAndHashCode.Include
    private RegistroMascota mascota;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_recordatorio", nullable = false)
    private TipoRecordatorio tipoRecordatorio;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "recordatorio_status")
    private RecordatorioStatus recordatorioStatus;
}
