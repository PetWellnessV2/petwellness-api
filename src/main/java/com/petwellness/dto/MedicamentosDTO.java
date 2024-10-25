package com.petwellness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class MedicamentosDTO {
    private Integer idMedicamento;

    @NotBlank(message = "La descripción es obligatorio")
    @Size(max = 50, message = "La descripción no debe ser de más 50 caracteres")
    private String descripcion;
    
    @NotBlank(message = "El nombre de la mascota es obligatorio")
    private String NomMascota;
}
