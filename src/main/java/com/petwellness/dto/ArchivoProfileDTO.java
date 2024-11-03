package com.petwellness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArchivoProfileDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String NomMascota;
    private String path;
}
