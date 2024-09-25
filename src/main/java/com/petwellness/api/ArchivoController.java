package com.petwellness.api;

import com.petwellness.dto.ArchivoDTO;
import com.petwellness.dto.ArchivoRegistroDTO;
import com.petwellness.service.ArchivoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archivos")
@RequiredArgsConstructor
public class ArchivoController {

    private final ArchivoService archivoService;

    // Crear un nuevo archivo
    @PostMapping
    public ResponseEntity<ArchivoRegistroDTO> createArchivo(@Valid @RequestBody ArchivoRegistroDTO archivoRegistroDTO) {
        ArchivoRegistroDTO newArchivo = archivoService.createArchivo(archivoRegistroDTO);
        return new ResponseEntity<>(newArchivo, HttpStatus.CREATED);
    }

    // Actualizar un archivo existente
    @PutMapping("/{id}")
    public ResponseEntity<ArchivoRegistroDTO> updateArchivo(
            @PathVariable Integer id,
            @Valid @RequestBody ArchivoRegistroDTO archivoRegistroDTO) {
        ArchivoRegistroDTO updateArchivo = archivoService.updateArchivo(id, archivoRegistroDTO);
        return new ResponseEntity<>(updateArchivo, HttpStatus.OK);
    }

    // Eliminar un archivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArchivo(@PathVariable Integer id) {
        archivoService.deleteArchivo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Obtener un archivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<ArchivoDTO> getArchivoById(@PathVariable Integer id) {
        ArchivoDTO archivo = archivoService.getArchivoById(id);
        return new ResponseEntity<>(archivo, HttpStatus.OK);
    }

    // Obtener todos los archivos
    @GetMapping
    public ResponseEntity<List<ArchivoDTO>> getAllArchivos() {
        List<ArchivoDTO> archivos = archivoService.getAllArchivos();
        return new ResponseEntity<>(archivos, HttpStatus.OK);
    }
}
