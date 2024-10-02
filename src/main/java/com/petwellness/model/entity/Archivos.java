package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "archivos")
public class Archivos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_archivos")
    private Integer idArchivos;

    @Column(name = "nombre_archivo", length = 50)
    private String nombreArchivo;

    @Column(name = "descripcion_archivo", length = 250)
    private String descripcionArchivo;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "registro_mascota_id_mascota", referencedColumnName = "id_mascota", foreignKey = @ForeignKey(name = "FK_archivos_registro_mascota"))
    private RegistroMascota registroMascota;
}
