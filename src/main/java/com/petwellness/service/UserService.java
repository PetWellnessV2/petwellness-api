package com.petwellness.service;

import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegistroDTO;

public interface UserService {
    UserProfileDTO registerUser(UserRegistroDTO userRegistroDTO);
    public UserProfileDTO registerVet(UserRegistroDTO userRegistroDTO);
    UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO);
    UserProfileDTO getUserProfileById(Integer id);

}
