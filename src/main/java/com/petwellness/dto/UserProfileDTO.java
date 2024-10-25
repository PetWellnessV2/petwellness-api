package com.petwellness.dto;

import com.petwellness.model.enums.ERole;
import com.petwellness.model.enums.Especialidad;
import com.petwellness.model.enums.InstitucionEducativa;
import com.petwellness.model.enums.TipoAlbergue;

import lombok.Data;


@Data
public class UserProfileDTO {
    private Integer id;
    private String email;
    private ERole role;
    private String firstName;
    private String lastName;
    private String telefono;
    private String shippingAddress;
    private InstitucionEducativa institucionEducativa;
    private Especialidad especialidad;
    private String nombreAlbergue;
    private TipoAlbergue tipoAlbergue;
    private String ruc;

}
