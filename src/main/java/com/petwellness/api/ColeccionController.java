package com.petwellness.api;

import com.petwellness.dto.ColeccionDTO;
import com.petwellness.service.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colecciones")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
public class ColeccionController {

    private final ColeccionService coleccionService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ColeccionDTO>> obtenerColeccionesDeUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(coleccionService.obtenerColeccionesDeUsuario(usuarioId));
    }

    @GetMapping
    public ResponseEntity<List<ColeccionDTO>> obtenerTodasLasColecciones() {
        return ResponseEntity.ok(coleccionService.obtenerTodasLasColecciones());
    }

    @PostMapping
    public ResponseEntity<ColeccionDTO> crearColeccion(@RequestBody ColeccionDTO coleccionDTO) {
        return new ResponseEntity<>(coleccionService.crearColeccion(coleccionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColeccionDTO> actualizarColeccion(@PathVariable Integer id, @RequestBody ColeccionDTO coleccionDTO) {
        return ResponseEntity.ok(coleccionService.actualizarColeccion(id, coleccionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarColeccion(@PathVariable Integer id) {
        coleccionService.eliminarColeccion(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{coleccionId}/productos/{productoId}")
    public ResponseEntity<ColeccionDTO> agregarProductoAColeccion(
            @PathVariable Integer coleccionId,
            @PathVariable Integer productoId) {
        return ResponseEntity.ok(coleccionService.agregarProductoAColeccion(coleccionId, productoId));
    }

    @DeleteMapping("/{coleccionId}/productos/{productoId}")
    public ResponseEntity<Void> eliminarProductoDeColeccion(@PathVariable Integer coleccionId, @PathVariable Integer productoId) {
        coleccionService.eliminarProductoDeColeccion(coleccionId, productoId);
        return ResponseEntity.noContent().build();
    }
}