package com.petwellness.model.entity;

import com.petwellness.model.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();

        @Column(name = "precio_total_pedido")
    private BigDecimal precioTotalPedido;

    public void calcularPrecioTotal() {
        this.precioTotalPedido = detalles.stream()
            .map(DetallePedido::getPrecioTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}