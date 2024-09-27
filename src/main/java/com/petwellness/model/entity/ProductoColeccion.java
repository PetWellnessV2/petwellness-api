package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Data
@Entity
@Table(name = "productos_coleccion")
public class ProductoColeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coleccion_id", nullable = false)
    @JsonBackReference
    private Coleccion coleccion;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
}