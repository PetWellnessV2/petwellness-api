package com.petwellness.dto;

import com.petwellness.model.enums.RecordatorioStatus;
import com.petwellness.model.enums.TipoRecordatorio;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordatorioDTO {
    private Integer recordatorioId;
    private Integer usuarioId;
    private Integer mascotaId;
    private TipoRecordatorio tipoRecordatorio;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaHora;
    private RecordatorioStatus recordatorioStatus;
}
