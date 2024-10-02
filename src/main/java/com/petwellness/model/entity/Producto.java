package com.petwellness.model.entity;

import com.petwellness.model.enums.TipoProducto;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Data
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre_producto", length = 50)
    private String nombreProducto;

    @Column(name = "imagen", length = 250)
    private String imagen;

    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Column(name = "costo")
    private BigDecimal costo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_producto")
    private TipoProducto tipoProducto;

    @Column(name = "stock")
    private Integer stock;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<ProductoColeccion> productosColeccion;


}
