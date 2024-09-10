package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "medicamentos")
public class Medicamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicamento")
    private Integer idMedicamento;

    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "registro_mascota_id_mascota", referencedColumnName = "id_mascota", foreignKey = @ForeignKey(name = "FK_medicamentos_registro_mascota"), nullable = false)
    private RegistroMascota registroMascota;
}
