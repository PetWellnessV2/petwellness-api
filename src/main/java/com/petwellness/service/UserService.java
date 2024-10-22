package com.petwellness.service;

import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegistroDTO;

public interface UserService {
    UserProfileDTO registerUser(UserRegistroDTO userRegistroDTO);
    UserProfileDTO updateUserProfile(UserProfileDTO userProfileDTO);
    UserProfileDTO getUserProfileById(String id);

}
