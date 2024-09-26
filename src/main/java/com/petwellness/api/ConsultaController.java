package com.petwellness.api;

import com.petwellness.model.entity.Consulta;
import com.petwellness.model.enums.EstadoConsulta;
import com.petwellness.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    public final ConsultaService consultaService;

    @GetMapping
    public ResponseEntity<List<Consulta>> getAllConsultas() {
        List<Consulta> consultas = consultaService.getAll();
        return new ResponseEntity<List<Consulta>>(consultas, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Consulta>> paginateConsultas(
            @PageableDefault(size = 5)Pageable pageable){
        Page<Consulta> consultas = consultaService.paginate(pageable);
        return new ResponseEntity<Page<Consulta>>(consultas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> getConsultabyId(@PathVariable("id") Integer id) {
        Consulta consulta = consultaService.findById(id);
        return new ResponseEntity<Consulta>(consulta, HttpStatus.OK);
    }

    @GetMapping("estado/{estado}")
    public ResponseEntity<List<Consulta>> getConsultasByEstado(@PathVariable("estado") EstadoConsulta estado) {
        List<Consulta> consultas = consultaService.findByEstadoConsulta(estado);
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Consulta> createConsulta(@RequestBody Consulta consulta) {
        Consulta newConsulta = consultaService.create(consulta);
        return new ResponseEntity<>(newConsulta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consulta> updateConsulta(@PathVariable("id") Integer id,
                                                   @RequestBody Consulta consulta) {
        Consulta updateConsulta = consultaService.update(id, consulta);
        return new ResponseEntity<Consulta>(updateConsulta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsulta(@PathVariable("id") Integer id) {
        consultaService.delete(id);
        return ResponseEntity.noContent().build();
    }






}
