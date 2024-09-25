package com.petwellness.api;

import com.petwellness.dto.MedicamentosDTO;
import com.petwellness.service.MedicamentosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/medicamentos")
public class MedicamentosController {

    private final MedicamentosService medicamentosService;

    @GetMapping
    public ResponseEntity<List<MedicamentosDTO>> getAllMedicamentos() {
        List<MedicamentosDTO> medicamentos = medicamentosService.getAllMedicamentos();
        return new ResponseEntity<>(medicamentos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentosDTO> getMedicamentoById(@PathVariable Integer id) {
        MedicamentosDTO medicamento = medicamentosService.getMedicamentoById(id);
        return new ResponseEntity<>(medicamento, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicamentosDTO> createMedicamento(@RequestBody MedicamentosDTO medicamentoDTO) {
        MedicamentosDTO nuevoMedicamento = medicamentosService.createMedicamento(medicamentoDTO);
        return new ResponseEntity<>(nuevoMedicamento, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentosDTO> updateMedicamento(
            @PathVariable Integer id,
            @RequestBody MedicamentosDTO medicamentoDTO) {
        MedicamentosDTO medicamentoActualizado = medicamentosService.updateMedicamento(id, medicamentoDTO);
        return new ResponseEntity<>(medicamentoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Integer id) {
        medicamentosService.deleteMedicamento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
