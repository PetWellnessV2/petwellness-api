package com.petwellness.api;

import com.petwellness.dto.ReporteVentasDTO;
import com.petwellness.service.ReporteVentasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN')")
public class ReporteVentasController {

    private final ReporteVentasService reporteVentasService;

    @GetMapping("/ventas")
    public ResponseEntity<List<ReporteVentasDTO>> generarReporteDeVentas() {
        List<ReporteVentasDTO> reporte = reporteVentasService.generarReporteDeVentas();
        return ResponseEntity.ok(reporte);
    }
}
