package com.petwellness.api;

import com.petwellness.dto.ProductoDTO;
import com.petwellness.service.RecomendacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recomendaciones")
@RequiredArgsConstructor
public class RecomendacionController {

    private final RecomendacionService recomendacionService;

    /**
     * Obtener recomendaciones específicas para una mascota
     * @param mascotaId el ID de la mascota
     * @return lista de productos recomendados para la mascota
     */
    @GetMapping("/{mascotaId}")
    public ResponseEntity<List<ProductoDTO>> getRecomendaciones(@PathVariable Integer mascotaId) {
        List<ProductoDTO> productosRecomendados = recomendacionService.getRecomendaciones(mascotaId);
        return ResponseEntity.ok(productosRecomendados);
    }

    /**
     * Obtener todas las recomendaciones generales
     * @return lista de todos los productos recomendados sin filtrar por mascota
     */
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllRecomendaciones() {
        List<ProductoDTO> productosRecomendados = recomendacionService.getAllRecomendaciones();
        return ResponseEntity.ok(productosRecomendados);
    }
}
