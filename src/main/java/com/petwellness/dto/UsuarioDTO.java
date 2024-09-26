package com.petwellness.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.petwellness.model.enums.TipoUser;
import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private Integer userId;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El campo debe ser un correo electrónico válido")
    private String email;
    @NotBlank(message = "El número telefónico es obligatorio")
    @Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos")
    private String telefono;
    private TipoUser tipoUsuario;
}
