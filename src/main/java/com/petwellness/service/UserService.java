package com.petwellness.service;

import com.petwellness.dto.AuthResponseDTO;
import com.petwellness.dto.LoginDTO;
import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegistroDTO;

public interface UserService {
    UserProfileDTO registerUser(UserRegistroDTO userRegistroDTO);
    public UserProfileDTO registerVet(UserRegistroDTO userRegistroDTO);
    AuthResponseDTO login(LoginDTO loginDTO);
    UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO);
    UserProfileDTO getUserProfileById(Integer id);

}
