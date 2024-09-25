package com.petwellness.api;

import com.petwellness.dto.MedicamentosDTO;
import com.petwellness.dto.MedicamentosRegistroDTO;
import com.petwellness.service.MedicamentosService;
import jakarta.validation.Valid;
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
    public ResponseEntity<MedicamentosRegistroDTO> createMedicamento(@Valid @RequestBody MedicamentosRegistroDTO medicamentosRegistroDTO) {
        MedicamentosRegistroDTO newMedicamento = medicamentosService.createMedicamento(medicamentosRegistroDTO);
        return new ResponseEntity<>(newMedicamento, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentosRegistroDTO> updateMedicamento(
            @PathVariable Integer id,
            @Valid @RequestBody MedicamentosRegistroDTO medicamentosRegistroDTO) {
        MedicamentosRegistroDTO updateMedicamento = medicamentosService.updateMedicamento(id, medicamentosRegistroDTO);
        return new ResponseEntity<>(updateMedicamento, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Integer id) {
        medicamentosService.deleteMedicamento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
