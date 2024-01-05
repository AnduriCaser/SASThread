package com.sast.sastthread.dto;

public record AuthenticationRequest(String username,
        String email,
        String password) {
}
