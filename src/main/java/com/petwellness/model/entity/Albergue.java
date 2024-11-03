package com.petwellness.model.entity;

import com.petwellness.model.enums.TipoAlbergue;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "albergue")
public class Albergue {
    @Id
    @Column(name="usuario_user_id")
    private Integer usuario_user_id;


    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoAlbergue tipoAlbergue;

    @Column(name = "ruc", length = 11, nullable = false)
    private String ruc;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "FK_User"))
    private User admin;
}
