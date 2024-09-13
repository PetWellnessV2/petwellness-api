package com.petwellness.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ArchivoDTO {
    private Integer idArchivos;
    private String nombreArchivo;
    private String descripcionArchivo;
    private LocalDate fecha;
    private Integer idMascota; // Relación con la mascota

}
