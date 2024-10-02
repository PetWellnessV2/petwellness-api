package com.petwellness.model.entity;

import com.petwellness.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "carrito_compra")
public class CarritoCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Integer idCompra;

    @ManyToOne
    @JoinColumn(name = "usuario_user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "FK_carrito_usuario"), nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "producto_id_producto", referencedColumnName = "id_producto", foreignKey = @ForeignKey(name = "FK_carrito_producto"), nullable = false)
    private Producto producto;

    @Column(name = "precio_total")
    private Double precioTotal;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
}
