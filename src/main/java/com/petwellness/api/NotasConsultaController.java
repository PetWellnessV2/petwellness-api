package com.petwellness.api;

import com.petwellness.dto.NotasConsultaDTO;
import com.petwellness.service.NotasConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notas-consulta")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'VETERINARIO')")
public class NotasConsultaController {

    private final NotasConsultaService notasConsultaService;

    // Obtener todas las notas de consulta
    @GetMapping
    public ResponseEntity<List<NotasConsultaDTO>> getAllNotasConsulta() {
        List<NotasConsultaDTO> notas = notasConsultaService.getAllNotasConsulta();
        return new ResponseEntity<>(notas, HttpStatus.OK);
    }

    // Obtener una nota de consulta por ID
    @GetMapping("/{id}")
    public ResponseEntity<NotasConsultaDTO> getNotasConsultaById(@PathVariable Integer id) {
        NotasConsultaDTO nota = notasConsultaService.getNotasConsultaById(id);
        return new ResponseEntity<>(nota, HttpStatus.OK);
    }

    // Crear una nueva nota de consulta
    @PostMapping
    public ResponseEntity<NotasConsultaDTO> createNotasConsulta(@Valid @RequestBody NotasConsultaDTO notasConsultaDTO) {
        NotasConsultaDTO newNota = notasConsultaService.createNotasConsulta(notasConsultaDTO);
        return new ResponseEntity<>(newNota, HttpStatus.CREATED);
    }

    // Actualizar una nota de consulta existente
    @PutMapping("/{id}")
    public ResponseEntity<NotasConsultaDTO> updateNotasConsulta(
            @PathVariable Integer id, @Valid @RequestBody NotasConsultaDTO notasConsultaDTO) {
        NotasConsultaDTO updatedNota = notasConsultaService.updateNotasConsulta(id, notasConsultaDTO);
        return new ResponseEntity<>(updatedNota, HttpStatus.OK);
    }

    // Eliminar una nota de consulta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotasConsulta(@PathVariable Integer id) {
        notasConsultaService.deleteNotasConsulta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mascotas/{mascotaId}/notas")
    public ResponseEntity<List<NotasConsultaDTO>> getConsultas(
            @PathVariable Integer mascotaId) {
        List<NotasConsultaDTO> consultas = notasConsultaService.findNotasConsultaByMascotaId(mascotaId);
        return ResponseEntity.ok(consultas);
    }
}
