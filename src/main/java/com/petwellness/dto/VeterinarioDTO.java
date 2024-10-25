package com.petwellness.dto;

import com.petwellness.model.enums.Especialidad;
import com.petwellness.model.enums.InstitucionEducativa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VeterinarioDTO{
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El campo debe ser un correo electrónico válido")
    private String email;

    @NotBlank(message = "El número telefónico es obligatorio")
    @Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @NotNull(message = "La institucion educativa es obligatoria")
    private InstitucionEducativa institucionEducativa;

    @NotNull(message = "La especialidad es obligatoria")
    private Especialidad especialidad;
}
