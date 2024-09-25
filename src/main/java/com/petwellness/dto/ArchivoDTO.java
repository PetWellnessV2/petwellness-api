package com.petwellness.dto;

import com.petwellness.model.enums.RecordatorioStatus;
import com.petwellness.model.enums.TipoRecordatorio;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ArchivoDTO {
    private Integer id;
    private Integer idMascota;
    private String titulo;
    private String descripcion;
    private LocalDate fechaHora;
}
