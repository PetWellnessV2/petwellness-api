package com.petwellness.mapper;

import com.petwellness.dto.AuthResponseDTO;
import com.petwellness.dto.LoginDTO;
import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegistroDTO;
import com.petwellness.model.entity.User;
import com.petwellness.model.entity.Veterinario;
import com.petwellness.model.enums.TipoUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public User toEntity(UserRegistroDTO registroDTO){
        User user = modelMapper.map(registroDTO, User.class);
        user.setContrasena(registroDTO.getContrasena());
        if (registroDTO.getInstitucionEducativa() != null) {
            Veterinario veterinario = new Veterinario();
            veterinario.setInstitucionEducativa(registroDTO.getInstitucionEducativa());
            veterinario.setEspecialidad(registroDTO.getEspecialidad());
            user.setVeterinario(veterinario);
        }

        return user;
    }

    public UserProfileDTO toUserProfileDto(User user){
        UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);
        if (user.getRole() != null) {
            userProfileDTO.setRole(user.getRole().getName());

        }
        if (user.getCustomer() != null) {
            userProfileDTO.setFirstName(user.getCustomer().getNombre());
            userProfileDTO.setLastName(user.getCustomer().getApellido());
            userProfileDTO.setShippingAddress(user.getCustomer().getShippingAddress());
            userProfileDTO.setTelefono(user.getCustomer().getTelefono());
            if (user.getVeterinario() != null) {
                userProfileDTO.setInstitucionEducativa(user.getVeterinario().getInstitucionEducativa());
                userProfileDTO.setEspecialidad(user.getVeterinario().getEspecialidad());
            }
        }

        return userProfileDTO;
    }

    //Convertir de LoginDTO a User (cuando procesas el login)
    public User toEntity(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }

    public AuthResponseDTO toAuthResponseDTO(User user, String token){
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);
        if (user.getCustomer() != null) {
            authResponseDTO.setFirstName(user.getCustomer().getNombre());
            authResponseDTO.setLastName(user.getCustomer().getApellido());
        } else if (user.getVeterinario() != null) {
            authResponseDTO.setFirstName(user.getVeterinario().getInstitucionEducativa().toString());
            authResponseDTO.setLastName(user.getVeterinario().getEspecialidad().toString());
        } else {
            authResponseDTO.setFirstName("Admin");
            authResponseDTO.setLastName("User");
        }

        authResponseDTO.setId(user.getUserId());
        authResponseDTO.setRole(user.getRole().getName().name());

        return authResponseDTO;
    }

}
