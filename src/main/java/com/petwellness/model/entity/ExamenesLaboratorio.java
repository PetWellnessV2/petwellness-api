package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "examenes_laboratorio")
public class ExamenesLaboratorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_examen_lab")
    private Integer idExamenLab;

    @Column(name = "nombre", length = 250, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 250, nullable = false)
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "registro_mascota_id_mascota", referencedColumnName = "id_mascota", foreignKey = @ForeignKey(name = "FK_examenes_laboratorio_registro_mascota"), nullable = false)
    private RegistroMascota registroMascota;
}
