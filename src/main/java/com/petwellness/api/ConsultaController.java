package com.petwellness.api;

import com.petwellness.dto.ConsultaDTO;
import com.petwellness.model.enums.EstadoConsulta;
import com.petwellness.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    private final ConsultaService consultaService;

    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> getAllConsultas() {
        List<ConsultaDTO> consultas = consultaService.getAll();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ConsultaDTO>> paginateConsultas(@PageableDefault(size = 5) Pageable pageable) {
        Page<ConsultaDTO> consultas = consultaService.paginate(pageable);
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> getConsultabyId(@PathVariable("id") Integer id) {
        ConsultaDTO consulta = consultaService.findById(id);
        return new ResponseEntity<>(consulta, HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ConsultaDTO>> getConsultasByEstado(@PathVariable("estado") EstadoConsulta estado) {
        List<ConsultaDTO> consultas = consultaService.findByEstadoConsulta(estado);
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConsultaDTO> createConsulta(@Valid @RequestBody ConsultaDTO consultaDTO) {
        ConsultaDTO newConsulta = consultaService.create(consultaDTO);
        return new ResponseEntity<>(newConsulta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> updateConsulta(@PathVariable("id") Integer id, @Valid @RequestBody ConsultaDTO consultaDTO) {
        ConsultaDTO updateConsulta = consultaService.update(id, consultaDTO);
        return new ResponseEntity<>(updateConsulta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsulta(@PathVariable("id") Integer id) {
        consultaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
