package com.petwellness.api;

import com.petwellness.model.entity.Usuario;
import com.petwellness.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final UsuarioService usuarioService;
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    @PostMapping("/usuarios/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioService.registerUsuario(usuario);
        return new ResponseEntity<>(newUsuario, HttpStatus.CREATED);
    }
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
