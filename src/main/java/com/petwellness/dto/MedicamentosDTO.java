package com.petwellness.dto;

import com.petwellness.model.entity.RegistroMascota;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicamentosDTO {
    private Integer idMedicamento;
    private String descripcion;
    private LocalDate fecha;
    private Integer idMascota;
}
