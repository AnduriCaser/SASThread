package com.sast.sastthread.jwt;
import io.jsonwebtoken.Claims;

import java.security.Key;

import com.sast.sastthread.security.CustomUserDetail;

public interface JwtService {

    Claims extractClaims(String token);

    Key getKey();

    String generateToken(CustomUserDetail customUserDetail);

    String refreshToken(CustomUserDetail customUserDetail);

    boolean isValidToken(String token);
}
