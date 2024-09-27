package com.petwellness.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HorariosDisponiblesDTO {
    private Integer idHorario;

    @NotNull(message = "El ID del veterinario es obligatorio")
    private Integer veterinarioId;

    @NotNull(message = "La hora es obligatoria")
    private Integer hora;

    @NotNull(message = "La fecha es obligatoria")
    @Future(message = "La fecha debe ser en el futuro")
    private LocalDate fecha;
}
