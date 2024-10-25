package com.petwellness.api;


import com.petwellness.dto.VeterinarioDTO;
import com.petwellness.dto.VeterinarioRegistroDTO;
import com.petwellness.service.UsuarioService;
import com.petwellness.service.VeterinarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;
    private final UsuarioService usuarioService;

    // Crear veterinario
    @PostMapping
    public ResponseEntity<VeterinarioRegistroDTO> crearVeterinario( @Valid @RequestBody VeterinarioRegistroDTO veterinario) {
        VeterinarioRegistroDTO nuevoVeterinario = veterinarioService.crearVeterinario(veterinario);

        return new ResponseEntity<>(nuevoVeterinario, HttpStatus.CREATED);
    }

    // Obtener todos los veterinarios
    @GetMapping
    public ResponseEntity<List<VeterinarioDTO>> obtenerVeterinarios() {
        List<VeterinarioDTO> veterinarios = veterinarioService.obtenerVeterinarios();
        return new ResponseEntity<>(veterinarios, HttpStatus.OK);
    }

    // Eliminar un veterinario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVeterinario(@PathVariable Integer id) {
        veterinarioService.eliminarVeterinario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Actualizar un veterinario
    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioRegistroDTO> actualizarVeterinario(@PathVariable Integer id, @Valid @RequestBody VeterinarioRegistroDTO veterinarioActualizado) {
        VeterinarioRegistroDTO veterinarioExistente = veterinarioService.actualizarVeterinario(id, veterinarioActualizado);
        return new ResponseEntity<>(veterinarioExistente, HttpStatus.OK);
    }
}
