package com.petwellness.dto;

import com.petwellness.model.enums.ERole;
import com.petwellness.model.enums.Especialidad;
import com.petwellness.model.enums.InstitucionEducativa;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserProfileDTO {
    private int id;
    private String email;
    private ERole role;
    private String firstName;
    private String lastName;

    private String shippingAddress;
    private InstitucionEducativa institucionEducativa;
    private Especialidad especialidad;
}
