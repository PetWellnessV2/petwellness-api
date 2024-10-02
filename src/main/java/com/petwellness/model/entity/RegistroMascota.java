package com.petwellness.model.entity;

import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;
import com.petwellness.model.enums.Raza;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "registro_mascota")
public class RegistroMascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Integer idMascota;

    @ManyToOne
    @JoinColumn(name = "usuario_user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "FK_registro_mascota_usuario"))
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "especie")
    private Especie especie;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(name = "raza")
    private Raza raza;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "foto", length = 250)
    private String foto;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Column(name = "direccion", length = 250)
    private String direccion;

    @Column(name = "miembro_id", length = 8)
    private String miembroID;

    @Column(name = "titular_poliza", length = 50)
    private String titularPoliza;

    @Column(name = "info_adicional", length = 250)
    private String infoAdicional;
}
