package com.petwellness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RecordatorioDTO {
    private Long id;
    
    @NotNull(message = "El ID de usuario es obligatorio")
    private Long usuarioId;
    
    @NotNull(message = "El ID de mascota es obligatorio")
    private Long mascotaId;
    
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    private String descripcion;
    
    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;
    
    private boolean completado;
}