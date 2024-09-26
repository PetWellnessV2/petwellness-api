package com.petwellness.api;

import com.petwellness.model.entity.Usuario;
import com.petwellness.model.entity.Veterinario;
import com.petwellness.service.UsuarioService;
import com.petwellness.service.VeterinarioService;
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
    public ResponseEntity<Veterinario> crearVeterinario(@RequestBody Veterinario veterinario) {
        if (veterinario.getUsuario() == null || veterinario.getUsuario().getUserId() == null) {
            Usuario nuevoUsuario = usuarioService.registerUsuario(veterinario.getUsuario());
            veterinario.setUsuario(nuevoUsuario);
        }
        Veterinario nuevoVeterinario = veterinarioService.crearVeterinario(veterinario);
        return new ResponseEntity<>(nuevoVeterinario, HttpStatus.CREATED);
    }

    // Obtener todos los veterinarios
    @GetMapping
    public ResponseEntity<List<Veterinario>> obtenerVeterinarios() {
        List<Veterinario> veterinarios = veterinarioService.obtenerVeterinarios();
        return new ResponseEntity<>(veterinarios, HttpStatus.OK);
    }

    // Eliminar un veterinario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVeterinario(@PathVariable Integer id) {
        veterinarioService.eliminarVeterinario(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar un veterinario
    @PutMapping("/{id}")
    public ResponseEntity<Veterinario> actualizarVeterinario(@PathVariable Integer id, @RequestBody Veterinario veterinarioActualizado) {
        Veterinario veterinarioExistente = veterinarioService.actualizarVeterinario(id, veterinarioActualizado);
        return new ResponseEntity<>(veterinarioExistente, HttpStatus.OK);
    }
}
