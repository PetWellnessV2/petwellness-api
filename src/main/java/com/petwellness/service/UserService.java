package com.petwellness.service;

import com.petwellness.dto.AuthResponseDTO;
import com.petwellness.dto.LoginDTO;
import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegisterDTO;

public interface UserService {
    UserProfileDTO registerCliente(UserRegisterDTO userRegistroDTO);
    UserProfileDTO registerVeterinario(UserRegisterDTO userRegistroDTO);
    UserProfileDTO registerAlbergue(UserRegisterDTO userRegistroDTO);
    AuthResponseDTO login(LoginDTO loginDTO);
    UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO);
    UserProfileDTO getUserProfileById(Integer id);

}
