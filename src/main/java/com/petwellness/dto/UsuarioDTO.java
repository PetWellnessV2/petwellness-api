package com.petwellness.dto;

import lombok.Data;
import com.petwellness.model.enums.TipoUser;
import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private Integer userId;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String contrasena;
    private TipoUser tipoUsuario;  // Enum para el tipo de usuario
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
