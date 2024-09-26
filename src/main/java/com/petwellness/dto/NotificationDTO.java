package com.petwellness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Integer id;
    @NotNull(message = "El nombre del usuario es obligatorio")
    private Integer usuarioId;
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(max = 50, message = "El mensaje no debe ser de más 50 caracteres")
    private String mensaje;
    @NotNull(message = "El estado del recordatorio es obligatorio")
    private boolean leida;
}
