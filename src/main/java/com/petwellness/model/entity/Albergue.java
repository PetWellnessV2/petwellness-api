package com.petwellness.model.entity;

import com.petwellness.model.enums.TipoAlbergue;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "albergues")
public class Albergue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_albergue", length = 50, nullable = false)
    private String nombreAlbergue;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_albergue", nullable = false)
    private TipoAlbergue tipoAlbergue;

    @Column(name = "ruc", length = 11, nullable = false)
    private String ruc;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
