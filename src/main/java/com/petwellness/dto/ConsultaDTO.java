package com.petwellness.dto;

import com.petwellness.model.enums.EstadoConsulta;
import com.petwellness.model.enums.TipoConsulta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaDTO {

    private Integer idConsulta;

    @NotNull(message = "El tipo de consulta es obligatorio")
    private TipoConsulta tipoConsulta;

    @NotNull(message = "El estado de la consulta es obligatorio")
    private EstadoConsulta estadoConsulta;

    @NotNull(message = "El ID del horario es obligatorio")
    private Integer idHorario;

    @NotNull(message = "El ID de la mascota es obligatorio")
    private Integer idMascota;

    @NotBlank(message = "La razón de la consulta es obligatoria")
    private String razonConsulta;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
