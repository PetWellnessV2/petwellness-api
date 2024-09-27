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

    @GetMapping("/{mascotaId}")
    public ResponseEntity<List<ProductoDTO>> getRecomendaciones(@PathVariable Integer mascotaId) {
        List<ProductoDTO> recomendaciones = recomendacionService.getRecomendaciones(mascotaId);
        return ResponseEntity.ok(recomendaciones);
    }
}
