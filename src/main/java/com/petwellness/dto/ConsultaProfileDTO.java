package com.petwellness.dto;

import com.petwellness.model.enums.EstadoConsulta;
import com.petwellness.model.enums.TipoConsulta;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ConsultaProfileDTO {
    private Integer idConsulta;
    private TipoConsulta tipoConsulta;
    private EstadoConsulta estadoConsulta;
    private LocalDate fecha;
    private LocalTime hora;
    private String nombre_mascota;
    private String razonConsulta;
}
