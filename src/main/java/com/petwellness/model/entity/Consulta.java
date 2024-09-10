package com.petwellness.model.entity;

import com.petwellness.model.enums.TipoConsulta;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "consulta")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Integer idConsulta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_consulta", nullable = false)
    private TipoConsulta tipoConsulta;

    @ManyToOne
    @JoinColumn(name = "horarios_disponibles_id_horario", referencedColumnName = "id_horario", foreignKey = @ForeignKey(name = "FK_consulta_horarios_disponibles"), nullable = false)
    private HorariosDisponibles horariosDisponibles;

    @ManyToOne
    @JoinColumn(name = "registro_mascota_id_mascota", referencedColumnName = "id_mascota", foreignKey = @ForeignKey(name = "FK_consulta_registro_mascota"), nullable = false)
    private RegistroMascota registroMascota;

    @Column(name = "razon_consulta", length = 250, nullable = false)
    private String razonConsulta;

}
