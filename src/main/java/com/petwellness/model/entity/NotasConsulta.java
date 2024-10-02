package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "notas_consulta")
public class NotasConsulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notas")
    private Integer idNotas;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "descripcion_nota", length = 250)
    private String descripcionNota;

    @ManyToOne
    @JoinColumn(name = "registro_mascota_id_mascota", referencedColumnName = "id_mascota", foreignKey = @ForeignKey(name = "FK_notas_consulta_registro_mascota"))
    private RegistroMascota registroMascota;
}
