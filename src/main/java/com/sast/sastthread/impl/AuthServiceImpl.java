package com.sast.sastthread.impl;

import org.springframework.stereotype.Service;

import com.sast.sastthread.repository.UserRepository;
import com.sast.sastthread.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;

    public AuthServiceImpl(final UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public void authenticate(String username, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
    }

    @Override
    public void logout(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }

    @Override
    public void resetPasswordWithToken(String resetToken, String newPassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetPasswordWithToken'");
    }

    @Override
    public void enableTwoFactorAuth(int userId, String secretKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enableTwoFactorAuth'");
    }

    @Override
    public void disableTwoFactorAuth(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableTwoFactorAuth'");
    }

    @Override
    public boolean isTwoFactorAuthEnabled(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isTwoFactorAuthEnabled'");
    }

    @Override
    public void authorize(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authorize'");
    }

    @Override
    public void register(String username, String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public void login(String username, String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public boolean isVerified(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isVerified'");
    }

    @Override
    public boolean isRegistered(int userId) {
        throw new UnsupportedOperationException("Unimplemented method 'isVerified'");
    }

    @Override
    public void verifyEmail(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyEmail'");
    }

}
