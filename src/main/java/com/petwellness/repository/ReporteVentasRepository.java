package com.petwellness.repository;

import com.petwellness.dto.ReporteVentasDTO;
import com.petwellness.model.entity.DetallePedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ReporteVentasRepository extends CrudRepository<DetallePedido, Integer> {

    @Query("SELECT dp FROM DetallePedido dp WHERE dp.pedido.estado = 'COMPLETADO'")
    List<DetallePedido> findAllCompletedSales();
}
