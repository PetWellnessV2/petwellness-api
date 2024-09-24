package com.petwellness.api;

import com.petwellness.dto.ArchivoDTO;
import com.petwellness.service.ArchivoService;
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
    public ResponseEntity<ArchivoDTO> createArchivo(@RequestBody ArchivoDTO archivoDTO) {
        ArchivoDTO nuevoArchivo = archivoService.createArchivo(archivoDTO);
        return new ResponseEntity<>(nuevoArchivo, HttpStatus.CREATED);
    }

    // Actualizar un archivo existente
    @PutMapping("/{id}")
    public ResponseEntity<ArchivoDTO> updateArchivo(@PathVariable Integer id, @RequestBody ArchivoDTO archivoDTO) {
        ArchivoDTO archivoActualizado = archivoService.updateArchivo(id, archivoDTO);
        return new ResponseEntity<>(archivoActualizado, HttpStatus.OK);
    }

    // Eliminar un archivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArchivo(@PathVariable Integer id) {
        archivoService.deleteArchivo(id);
        return ResponseEntity.noContent().build();
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
