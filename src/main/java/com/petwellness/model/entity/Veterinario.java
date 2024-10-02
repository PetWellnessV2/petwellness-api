package com.petwellness.model.entity;

import com.petwellness.model.enums.Especialidad;
import com.petwellness.model.enums.InstitucionEducativa;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name= "veterinario")
public class Veterinario {
    @Id
    @Column(name = "usuario_user_id")
    private Integer usuario_user_id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "FK_veterinario_usuario"))
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "institucion_educativa")
    private InstitucionEducativa institucionEducativa;

    @Enumerated(EnumType.STRING)
    @Column(name = "especialidad")
    private Especialidad especialidad;
}
