package com.petwellness.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class RecordatorioDTO {

    private Integer idRecordatorio;
    private String tipoRecordatorio;
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;
    private Boolean completado;
    private Integer mascotaId;
    private Integer usuarioId;
    private LocalDateTime fechaHora;
    private String titulo;
}
