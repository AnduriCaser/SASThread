package com.sast.sastthread.service;

public interface AuthService {

    void authenticate(String username, String password);

    void authorize(String username);

    void logout(String username);

    void register(String username, String email, String password);

    void login(String username, String email, String password);

    void resetPasswordWithToken(String resetToken, String newPassword);

    void enableTwoFactorAuth(int userId, String secretKey);

    void disableTwoFactorAuth(int userId);

    boolean isTwoFactorAuthEnabled(int userId);

    void verifyEmail(int userId);

    boolean isVerified(int userId);

    boolean isRegistered(int userId);

}
