package com.petwellness.api;

import com.petwellness.dto.ExamenFisicoDTO;
import com.petwellness.service.ExamenFisicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/examenes-fisicos")
@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'VETERINARIO')")
public class ExamenFisicoController {
    private final ExamenFisicoService examenFisicoService;

    @PostMapping
    public ResponseEntity<ExamenFisicoDTO> crearExamenFisico(@Valid @RequestBody ExamenFisicoDTO examenFisicoDTO) {
        ExamenFisicoDTO nuevoExamen = examenFisicoService.crearExamenFisico(examenFisicoDTO);
        return new ResponseEntity<>(nuevoExamen, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamenFisicoDTO> actualizarExamenFisico(@PathVariable Integer id, @Valid @RequestBody ExamenFisicoDTO examenFisicoDTO) {
        ExamenFisicoDTO examenActualizado = examenFisicoService.actualizarExamenFisico(id, examenFisicoDTO);
        return new ResponseEntity<>(examenActualizado, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ExamenFisicoDTO>> obtenerExamenesFisicos() {
        List<ExamenFisicoDTO> examenes = examenFisicoService.obtenerExamenesFisicos();
        return new ResponseEntity<>(examenes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamenFisicoDTO> obtenerExamenFisicoPorId(@PathVariable Integer id) {
        ExamenFisicoDTO examenFisico = examenFisicoService.obtenerExamenFisicoPorId(id);
        return new ResponseEntity<>(examenFisico, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarExamenFisico(@PathVariable Integer id) {
        examenFisicoService.eliminarExamenFisico(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/mascotas/{mascotaId}/examenes-fisicos")
    public ResponseEntity<List<ExamenFisicoDTO>> getExamenesFisicos(
            @PathVariable Integer mascotaId) {
        List<ExamenFisicoDTO> examenesFisicos = examenFisicoService.findExamenesFisicosByMascotaId(mascotaId);
        return ResponseEntity.ok(examenesFisicos);
    }
}
