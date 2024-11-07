package com.petwellness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NotasConsultaDTO {
    private Integer idNotas;
    private LocalDate fecha;
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    @NotNull(message = "El id de la mascota es obligatoria")
    private Integer idMascota;
}
