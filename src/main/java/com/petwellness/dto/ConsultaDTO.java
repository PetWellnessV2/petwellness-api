package com.petwellness.dto;

import lombok.Data;

@Data
public class ConsultaDTO {
    private Integer id;
    private String tipoConsulta;
    private Integer horarioDisponibleId;
    private String razonConsulta;
    private Integer idMascota;
}
