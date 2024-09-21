package com.petwellness.api;

import com.petwellness.dto.RecordatorioDTO;
import com.petwellness.service.RecordatorioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recordatorio")
@RequiredArgsConstructor
public class RecordatorioController {

    private final RecordatorioService recordatorioService;

    @PostMapping
    public ResponseEntity<?> crearRecordatorio(@Valid @RequestBody RecordatorioDTO recordatorioDTO) {
        try {
            RecordatorioDTO nuevoRecordatorio = recordatorioService.crearRecordatorio(recordatorioDTO);
            return new ResponseEntity<>(nuevoRecordatorio, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRecordatorio(@PathVariable Integer id) {
        try {
            recordatorioService.eliminarRecordatorio(id);
            return new ResponseEntity<>("Recordatorio eliminado exitosamente", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}