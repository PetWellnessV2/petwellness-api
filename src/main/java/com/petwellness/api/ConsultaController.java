package com.petwellness.api;

import com.petwellness.dto.ConsultaProfileDTO;
import com.petwellness.dto.ConsultaRegistroDTO;
import com.petwellness.model.enums.EstadoConsulta;
import com.petwellness.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/consultas")
@PreAuthorize("hasAnyRole('CUSTOMER', 'VETERINARIO')")
public class ConsultaController {
    private final ConsultaService consultaService;

    @GetMapping
    public ResponseEntity<List<ConsultaProfileDTO>> getAllConsultas() {
        List<ConsultaProfileDTO> consultas = consultaService.getAll();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/mascotas/{mascotaId}/consultas")
    public ResponseEntity<List<ConsultaProfileDTO>> getConsultas(
            @PathVariable Integer mascotaId) {
        List<ConsultaProfileDTO> consultas = consultaService.findConsultasByMascotaId(mascotaId);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ConsultaProfileDTO>> paginateConsultas(@PageableDefault(size = 5) Pageable pageable) {
        Page<ConsultaProfileDTO> consultas = consultaService.paginate(pageable);
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaProfileDTO> getConsultabyId(@PathVariable("id") Integer id) {
        ConsultaProfileDTO consulta = consultaService.findById(id);
        return new ResponseEntity<>(consulta, HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ConsultaProfileDTO>> getConsultasByEstado(@PathVariable("estado") EstadoConsulta estado) {
        List<ConsultaProfileDTO> consultas = consultaService.findByEstadoConsulta(estado);
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConsultaProfileDTO> createConsulta(@Valid @RequestBody ConsultaRegistroDTO consultaRegistroDTO) {
        ConsultaProfileDTO newConsulta = consultaService.create(consultaRegistroDTO);
        return new ResponseEntity<>(newConsulta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaProfileDTO> updateConsulta(@PathVariable("id") Integer id, @Valid @RequestBody ConsultaRegistroDTO consultaRegistroDTO) {
        ConsultaProfileDTO updateConsulta = consultaService.update(id, consultaRegistroDTO);
        return new ResponseEntity<>(updateConsulta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsulta(@PathVariable("id") Integer id) {
        consultaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Posponer consulta
    @PutMapping("/consultas/{consultaId}")
    public ResponseEntity<ConsultaProfileDTO> posponerConsulta(
            @PathVariable Integer consultaId,
            @RequestParam LocalTime nuevaHora,
            @RequestParam String nuevaFecha,
            @RequestParam Integer veterinarioUserId) {

        ConsultaProfileDTO consulta = consultaService.posponerConsulta(consultaId, nuevaHora, nuevaFecha, veterinarioUserId);
        return ResponseEntity.ok(consulta);
    }
}
