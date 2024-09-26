package com.petwellness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ArchivoRegistroDTO {
    private Integer id;
    @NotBlank(message = "El nombre del archivo es obligatorio")
    @Size(max = 25, message = "El nombre del archivo no debe ser de más 50 caracteres")
    private String nombreArchivo;
    @NotBlank(message = "La descripción es obligatorio")
    @Size(max = 50, message = "La descripción no debe ser de más 50 caracteres")
    private String descripcion;
    @NotNull(message = "El ID de la mascota es obligatorio")
    private Integer idRegistroMascota;
    private String path;
}
