package com.petwellness.api;

import com.petwellness.model.entity.Veterinario;
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

    //Crear un nuevo veterinario
    @PostMapping
    public ResponseEntity<Veterinario> crearVeterinario(@RequestBody Veterinario veterinario) {
        Veterinario nuevoVeterinario = veterinarioService.crearVeterinario(veterinario);
        return new ResponseEntity<>(nuevoVeterinario, HttpStatus.CREATED);
    }

    //Editar un veterinario existente
    @PutMapping("/{id}")
    public ResponseEntity<Veterinario> actualizarVeterinario(@PathVariable Integer id, @RequestBody Veterinario veterinario) {
        Veterinario veterinarioActualizado = veterinarioService.actualizarVeterinario(id, veterinario);
        return new ResponseEntity<>(veterinarioActualizado, HttpStatus.OK);
    }

    //Eliminar un veterinario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVeterinario(@PathVariable Integer id) {
        veterinarioService.eliminarVeterinario(id);
        return ResponseEntity.noContent().build();
    }

    //Obtener todos los veterinarios
    @GetMapping
    public ResponseEntity<List<Veterinario>> obtenerVeterinarios() {
        List<Veterinario> veterinarios = veterinarioService.obtenerVeterinarios();
        return new ResponseEntity<>(veterinarios, HttpStatus.OK);
    }
}
