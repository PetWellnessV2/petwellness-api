package com.petwellness.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VeterinarioDTO{
    @NotBlank(message = "La institucion educativa es obligatoria")
    private String institucionEducativa;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;
}
