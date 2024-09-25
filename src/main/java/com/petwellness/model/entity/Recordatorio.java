package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "recordatorios")
public class Recordatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recordatorio")
    private Integer idRecordatorio;

    @Column(name = "tipo_recordatorio")
    private String tipoRecordatorio;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private LocalTime hora;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "completado")
    private Boolean completado;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private RegistroMascota mascota;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "titulo")
    private String titulo;

}
