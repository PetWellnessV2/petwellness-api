package com.petwellness.api;

import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegistroDTO;
import com.petwellness.dto.UsuarioDTO;
import com.petwellness.dto.UsuarioRegistroDTO;
import com.petwellness.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class UsuarioController {
    private final UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UserProfileDTO>> getAll() {
        List<UserProfileDTO> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Obtener todos los usuarios
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getById(@PathVariable Integer id) {
        UserProfileDTO usuario = usuarioService.getUsuarioById(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    // Registrar un nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioRegistroDTO> register(@Valid @RequestBody UserRegistroDTO usuarioRegistroDTO) {
        UsuarioRegistroDTO newUsuario = usuarioService.registerUsuario(usuarioRegistroDTO);
        return new ResponseEntity<>(newUsuario, HttpStatus.CREATED);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRegistroDTO> updateUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioRegistroDTO usuarioRegistroDTO) {
        UsuarioRegistroDTO updatedUsuario = usuarioService.updateUsuario(id, usuarioRegistroDTO);
        return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
    }
}
