package com.petwellness.service.impl;

import com.petwellness.model.entity.Medicamentos;
import com.petwellness.model.entity.Producto;
import com.petwellness.repository.MedicamentosRepository;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.service.RecomendacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecomendacionServiceImpl implements RecomendacionService {

    private final MedicamentosRepository medicamentosRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> recomendarProductos(Integer idMascota) {
        // Obtener los medicamentos prescritos a la mascota
        List<Medicamentos> medicamentos = medicamentosRepository.findByRegistroMascotaIdMascota(idMascota);

        // Usamos un Set para evitar duplicados
        Set<Producto> productosRecomendados = new HashSet<>();

        for (Medicamentos medicamento : medicamentos) {
            String descripcionMedicamento = medicamento.getDescripcion();

            // Buscar productos que contengan en su nombre el medicamento prescrito
            List<Producto> productos = productoRepository.findByNombreProductoContainingIgnoreCase(descripcionMedicamento);

            productosRecomendados.addAll(productos);
        }

        // Convertimos el Set a una Lista y la retornamos
        return new ArrayList<>(productosRecomendados);
    }
}
