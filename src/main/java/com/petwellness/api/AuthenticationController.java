package com.petwellness.api;

import com.petwellness.dto.UsuarioDTO;
import com.petwellness.model.entity.Usuario;
import com.petwellness.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();

        // Convertir la lista de Usuario a UsuarioDTO
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuariosDTO);
    }

    // Registrar un nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> register(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario newUsuario = convertToEntity(usuarioDTO);
        Usuario savedUsuario = usuarioService.registerUsuario(newUsuario);

        return new ResponseEntity<>(convertToDTO(savedUsuario), HttpStatus.CREATED);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Método PUT para actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        Optional<Usuario> existingUsuarioOpt = usuarioService.getUsuarioById(id);
        if (!existingUsuarioOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Usuario existingUsuario = existingUsuarioOpt.get();
        existingUsuario.setNombre(usuarioDTO.getNombre());
        existingUsuario.setApellido(usuarioDTO.getApellido());
        existingUsuario.setEmail(usuarioDTO.getEmail());
        existingUsuario.setTelefono(usuarioDTO.getTelefono());
        existingUsuario.setContrasena(usuarioDTO.getContrasena());
        existingUsuario.setTipoUsuario(usuarioDTO.getTipoUsuario());
        existingUsuario.setUpdatedAt(LocalDateTime.now());

        Usuario updatedUsuario = usuarioService.registerUsuario(existingUsuario);
        return new ResponseEntity<>(convertToDTO(updatedUsuario), HttpStatus.OK);
    }


    // Método para convertir Usuario a UsuarioDTO
    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setUserId(usuario.getUserId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setContrasena(usuario.getContrasena());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        dto.setCreatedAt(usuario.getCreatedAt());
        dto.setUpdatedAt(usuario.getUpdatedAt());
        return dto;
    }

    // Método para convertir UsuarioDTO a Usuario
    private Usuario convertToEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUserId(dto.getUserId());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setContrasena(dto.getContrasena());  // Asigna la contraseña
        usuario.setTipoUsuario(dto.getTipoUsuario());
        usuario.setCreatedAt(dto.getCreatedAt());
        usuario.setUpdatedAt(dto.getUpdatedAt());
        return usuario;
    }

}
