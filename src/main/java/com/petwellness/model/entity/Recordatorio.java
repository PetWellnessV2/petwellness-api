package com.petwellness.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recordatorios")
public class Recordatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    @NotNull(message = "La mascota es obligatoria")
    private RegistroMascota mascota;

    @Column(nullable = false)
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @Column
    private String descripcion;

    @Column(nullable = false)
    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private boolean completado = false;
}