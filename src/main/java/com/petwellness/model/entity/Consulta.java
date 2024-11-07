package com.petwellness.model.entity;

import com.petwellness.model.enums.EstadoConsulta;
import com.petwellness.model.enums.TipoConsulta;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "consulta")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Integer idConsulta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_consulta")
    private TipoConsulta tipoConsulta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_consulta", nullable = false)
    private EstadoConsulta estadoConsulta;

    @ManyToOne
    @JoinColumn(name = "horarios_disponibles_id_horario", referencedColumnName = "id_horario", foreignKey = @ForeignKey(name = "FK_horarios_disponibles"))
    private HorariosDisponibles horariosDisponibles;

    @ManyToOne
    @JoinColumn(name = "mascota_id_mascota", referencedColumnName = "id_mascota", foreignKey = @ForeignKey(name = "FK_mascota"))
    private Mascota mascota;

    @Column(name = "razon_consulta", length = 250)
    private String razonConsulta;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
