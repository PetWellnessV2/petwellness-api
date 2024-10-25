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
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_horarios_user"), nullable = false)
    private User user;

    @Column(name = "hora", nullable = false)
    private Integer hora;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

}
