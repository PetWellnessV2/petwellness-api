package com.petwellness.mapper;

import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegistroDTO;
import com.petwellness.model.entity.User;
import com.petwellness.model.enums.TipoUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public User toEntity(UserRegistroDTO registroDTO){
        return modelMapper.map(registroDTO, User.class);
    }
    public UserProfileDTO toUserProfileDto(User user){
        UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);
        if(user.getCustomer() != null){
            if(user.getCustomer().getTipoUsuario() != TipoUser.DUEÑO) {
                userProfileDTO.setFirstName(user.getCustomer().getNombre());
                userProfileDTO.setLastName(user.getCustomer().getApellido());
                userProfileDTO.setShippingAddress(user.getCustomer().getShippingAddress());
            }
            if(user.getCustomer().getTipoUsuario() != TipoUser.VETERINARIO) {
                userProfileDTO.setFirstName(user.getCustomer().getNombre());
                userProfileDTO.setLastName(user.getCustomer().getApellido());
                userProfileDTO.setShippingAddress(user.getCustomer().getShippingAddress());
                userProfileDTO.setInstitucionEducativa(user.getCustomer().getUser().getVeterinario().getInstitucionEducativa());
                userProfileDTO.setEspecialidad(user.getCustomer().getUser().getVeterinario().getEspecialidad());
            }
        }
        return userProfileDTO;
    }
}
