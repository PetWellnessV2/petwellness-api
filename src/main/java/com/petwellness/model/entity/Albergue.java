package com.petwellness.model.entity;

import com.petwellness.model.enums.TipoAlbergue;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "albergue")
public class Albergue {
    @Id
    @Column(name="usuario_user_id")
    private Integer idUsuario;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "FK_albergue_usuario"))
    private Usuario usuario;

    @Column(name = "nombre_albergue", length = 50, nullable = false)
    private String nombreAlbergue;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_albergue", nullable = false)
    private TipoAlbergue tipoAlbergue;

    @Column(name = "ruc", length = 11, nullable = false)
    private String ruc;

}
