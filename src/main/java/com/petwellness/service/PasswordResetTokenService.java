package com.petwellness.service;

import com.petwellness.model.entity.PasswordResetToken;

public interface PasswordResetTokenService {
    void creatAndSendPasswordResetToken(String email) throws Exception;
    PasswordResetToken findByToken(String token);
    void removeResetToken(PasswordResetToken passwordResetToken);
    boolean isValidToken(String token);
    void resetPassword(String token, String newPassword);
}
