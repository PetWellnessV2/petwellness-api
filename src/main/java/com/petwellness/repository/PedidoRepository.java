package com.petwellness.repository;

import com.petwellness.model.entity.Pedido;
import com.petwellness.model.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioId(Integer usuarioId);
    Optional<Pedido> findTopByUsuarioIdAndEstadoOrderByFechaPedidoDesc(Integer usuarioId, EstadoPedido estado);
}