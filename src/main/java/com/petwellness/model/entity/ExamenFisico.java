package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "examen_fisico")
public class ExamenFisico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_examen")
    private Integer idExamen;

    @ManyToOne
    @JoinColumn(name = "registro_mascota_id_mascota", referencedColumnName = "id_mascota", foreignKey = @ForeignKey(name = "FK_examen_fisico_registro_mascota"), nullable = false)
    private RegistroMascota registroMascota;

    @Column(name = "presion_arterial", nullable = false)
    private Integer presionArterial;

    @Column(name = "pulso", nullable = false)
    private Integer pulso;

    @Column(name = "temperatura", nullable = false)
    private Float temperatura;

    @Column(name = "peso", nullable = false)
    private Float peso;

    @Column(name = "altura", nullable = false)
    private Float altura;
}
