package com.petwellness.service;

import com.petwellness.dto.AuthResponseDTO;
import com.petwellness.dto.LoginDTO;
import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegistroDTO;

import java.util.List;

public interface UserService {
    UserProfileDTO registerUser(UserRegistroDTO userRegistroDTO);
    UserProfileDTO registerVet(UserRegistroDTO userRegistroDTO);
    UserProfileDTO registerAdmin(UserRegistroDTO userRegistroDTO);
    void encryptExistingPasswords();
    AuthResponseDTO login(LoginDTO loginDTO);
    UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO);
    UserProfileDTO getUserProfileById(Integer id);
    List<UserProfileDTO> getAll();
}
