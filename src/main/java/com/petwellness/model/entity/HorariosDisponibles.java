package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "horarios_disponibles")
public class HorariosDisponibles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Integer idHorario;

    @ManyToOne
    @JoinColumn(name = "vet_user_id", referencedColumnName = "usuario_user_id", foreignKey = @ForeignKey(name = "FK_horarios_veterinario"))
    private Veterinario veterinario;

    @Column(name = "hora")
    private Integer hora;

    @Column(name = "fecha")
    private LocalDate fecha;

}
