package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre_producto", length = 50, nullable = false)
    private String nombreProducto;

    @Column(name = "imagen", length = 250, nullable = false)
    private String imagen;

    @Column(name = "descripcion", length = 250, nullable = false)
    private String descripcion;

    @Column(name = "costo", nullable = false)
    private BigDecimal costo;

    @ManyToOne
    @JoinColumn(name = "categoria_producto_id", referencedColumnName = "id", nullable = false)
    private CategoriaProducto categoriaProducto;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ProductoColeccion> productosColeccion = new HashSet<>();
}