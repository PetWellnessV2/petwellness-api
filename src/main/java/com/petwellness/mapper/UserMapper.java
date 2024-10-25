package com.petwellness.mapper;

import com.petwellness.dto.AuthResponseDTO;
import com.petwellness.dto.LoginDTO;
import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegisterDTO;
import com.petwellness.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public User toEntity(UserRegisterDTO registroDTO) {
        return modelMapper.map(registroDTO, User.class);
    }

    public UserProfileDTO toUserProfileDTO(User user) {
        UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);
        if (user.getCliente() != null) {
            userProfileDTO.setFirstName(user.getCliente().getNombre());
            userProfileDTO.setLastName(user.getCliente().getApellido());
            userProfileDTO.setShippingAddress(user.getCliente().getShippingAddress());
            userProfileDTO.setTelefono(user.getCliente().getTelefono());
        }
        if (user.getVeterinario() != null) {
            userProfileDTO.setFirstName(user.getVeterinario().getNombre());
            userProfileDTO.setLastName(user.getVeterinario().getApellido());
            userProfileDTO.setInstitucionEducativa(user.getVeterinario().getInstitucionEducativa());
            userProfileDTO.setEspecialidad(user.getVeterinario().getEspecialidad());
        }
        if (user.getAlbergue() != null) {
            userProfileDTO.setNombreAlbergue(user.getAlbergue().getNombreAlbergue());
            userProfileDTO.setTipoAlbergue(user.getAlbergue().getTipoAlbergue());
            userProfileDTO.setRuc(user.getAlbergue().getRuc());
        }

        return userProfileDTO;

    }

    // Convertir de LoginDTO a User (cuando procesas el login)
    public User toEntity(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }

    // Convertir de User a AuthResponseDTO para la respuesta de autenticación
    public AuthResponseDTO toAuthResponseDTO(User user, String token) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);

        // Obtener el nombre y apellido
        // Obtener el nombre
        String firstName = (user.getCliente() != null) ? user.getCliente().getNombre()
                : (user.getVeterinario() != null) ? "Veterinario"
                        : (user.getAlbergue() != null) ? user.getAlbergue().getNombreAlbergue()
                                : "Admin";

        // Obtener el apellido
        String lastName = (user.getCliente() != null) ? user.getCliente().getApellido()
                : (user.getVeterinario() != null) ? "Veterinario"
                        : (user.getAlbergue() != null) ? "Albergue"
                                : "User";

        authResponseDTO.setFirstName(firstName);
        authResponseDTO.setLastName(lastName);

        authResponseDTO.setRole(user.getRole().getName().name());

        return authResponseDTO;
    }

}
