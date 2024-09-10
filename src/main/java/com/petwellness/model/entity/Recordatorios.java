package com.petwellness.model.entity;

import com.petwellness.model.enums.TipoRecordatorio;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recordatorios")
public class Recordatorios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recordatorio")
    private Integer idRecordatorio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_recordatorio", nullable = false)
    private TipoRecordatorio tipoRecordatorio;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalDateTime hora;

    @Column(name = "descripcion", length = 250, nullable = false)
    private String descripcion;
}
