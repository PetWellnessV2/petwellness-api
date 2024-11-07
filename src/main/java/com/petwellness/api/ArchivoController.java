    package com.petwellness.api;
    
    import com.petwellness.dto.ArchivoProfileDTO;
    import com.petwellness.dto.ArchivoRegistroDTO;
    import com.petwellness.service.ArchivoService;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.List;
    
    @RestController
    @RequestMapping("/archivos")
    @RequiredArgsConstructor
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'VETERINARIO')")
    public class ArchivoController {
    
        private final ArchivoService archivoService;
    
        // Crear un nuevo archivo
        @PostMapping("/{archivoId}")
        public ResponseEntity<ArchivoProfileDTO> createArchivo(
                @Valid @RequestBody ArchivoRegistroDTO archivoRegistroDTO,
                @PathVariable("archivoId") String archivoId) {
            ArchivoProfileDTO newArchivo = archivoService.createArchivo(archivoRegistroDTO, archivoId);
            return new ResponseEntity<>(newArchivo, HttpStatus.CREATED);
        }
    
        // Actualizar un archivo existente
        @PutMapping("/{id}")
        public ResponseEntity<ArchivoProfileDTO> updateArchivo(
                @PathVariable Integer id,
                @Valid @RequestBody ArchivoRegistroDTO archivoRegistroDTO) {
            ArchivoProfileDTO updateArchivo = archivoService.updateArchivo(id, archivoRegistroDTO);
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
        public ResponseEntity<ArchivoProfileDTO> getArchivoById(@PathVariable Integer id) {
            ArchivoProfileDTO archivo = archivoService.getArchivoById(id);
            return new ResponseEntity<>(archivo, HttpStatus.OK);
        }

        // Obtener todos los archivos
        @GetMapping
        public ResponseEntity<List<ArchivoProfileDTO>> getAllArchivos() {
            List<ArchivoProfileDTO> archivos = archivoService.getAllArchivos();
            return new ResponseEntity<>(archivos, HttpStatus.OK);
        }
    }
