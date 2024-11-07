package com.petwellness.dto;

import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;
import com.petwellness.model.enums.Raza;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MascotaRegistroDTO {

    private Integer idMascota;
    private Integer usuarioId;

    @NotNull(message = "La especie es obligatoria")
    private Especie especie;

    @NotNull(message = "El género es obligatorio")
    private Genero genero;

    @NotNull(message = "La raza es obligatoria")
    private Raza raza;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de la mascota debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer edad;

    private String foto;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    private String descripcion;

    private String direccion;

    private String miembroID;

    private String titularPoliza;

    private String infoAdicional;

}
