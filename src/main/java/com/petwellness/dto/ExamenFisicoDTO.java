package com.petwellness.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExamenFisicoDTO {
    private Integer idExamen;

    @NotNull(message = "El ID de la mascota es obligatorio")
    private Integer idMascota;

    @NotNull(message = "La presión arterial es obligatoria")
    private Integer presionArterial;

    @NotNull(message = "El pulso es obligatorio")
    private Integer pulso;

    @NotNull(message = "La temperatura es obligatoria")
    private Float temperatura;

    @NotNull(message = "El peso es obligatorio")
    private Float peso;

    @NotNull(message = "La altura es obligatoria")
    private Float altura;
}
