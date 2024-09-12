package com.petwellness.dto;

import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;
import com.petwellness.model.enums.Raza;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistroMascotaDTO {

    private Integer idMascota;
    private Integer usuarioId; // Asociado al usuario
    private Especie especie;
    private Genero genero;
    private Raza raza;
    private String nombre;
    private Integer edad;
    private String foto;
    private LocalDate fechaNacimiento;
    private String descripcion;
    private String direccion;
    private String miembroID;
    private String titularPoliza;
    private String infoAdicional;
}
