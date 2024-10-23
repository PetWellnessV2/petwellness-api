package com.petwellness.model.entity;

import com.petwellness.model.enums.TipoEventoSalud;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class EventoSalud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private RegistroMascota mascota;

    @Enumerated(EnumType.STRING)
    private TipoEventoSalud tipoEvento; // VACUNACION, CHEQUEO, etc.

    private LocalDate fechaEvento;

    private String descripcion;
}
