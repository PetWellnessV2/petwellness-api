package com.petwellness.dto;

import com.petwellness.model.enums.RecordatorioStatus;
import com.petwellness.model.enums.TipoRecordatorio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ArchivoDTO {
    private Integer id;
    @NotBlank(message = "El nombre del archivo es obligatorio")
    @Size(max = 25, message = "El nombre del archivo no debe ser de más 50 caracteres")
    private String nombreArchivo;
    @NotBlank(message = "La descripción es obligatorio")
    @Size(max = 50, message = "La descripción no debe ser de más 50 caracteres")
    private String descripcion;
    @NotBlank(message = "El nombre de la mascota es obligatorio")
    private String NomMascota;
}
