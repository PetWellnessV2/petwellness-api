package com.petwellness.dto;

import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;
import com.petwellness.model.enums.Raza;
import lombok.Data;

@Data
public class MascotaProfileDTO {
    private Integer idMascota;
    private String nombre;
    private Especie especie;
    private Raza raza;
    private Genero genero;
    private Integer edad;
    private Integer idCustomer;
}
