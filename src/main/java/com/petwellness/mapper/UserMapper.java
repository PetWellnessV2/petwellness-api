package com.petwellness.mapper;

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

}
