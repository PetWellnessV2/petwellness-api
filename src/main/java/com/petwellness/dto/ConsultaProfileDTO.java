package com.petwellness.dto;

import com.petwellness.model.enums.EstadoConsulta;
import com.petwellness.model.enums.TipoConsulta;
import lombok.Data;

@Data
public class ConsultaProfileDTO {
    private Integer idConsulta;
    private TipoConsulta tipoConsulta;
    private EstadoConsulta estadoConsulta;
    private Integer idHorario;
    private Integer idMascota;
    private String razonConsulta;
}
