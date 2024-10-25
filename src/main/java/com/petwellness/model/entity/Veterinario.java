package com.petwellness.model.entity;

import com.petwellness.model.enums.Especialidad;
import com.petwellness.model.enums.InstitucionEducativa;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name= "veterinarios")
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "institucion_educativa", nullable = false)
    private InstitucionEducativa institucionEducativa;

    @Enumerated(EnumType.STRING)
    @Column(name = "especialidad", nullable = false)
    private Especialidad especialidad;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
