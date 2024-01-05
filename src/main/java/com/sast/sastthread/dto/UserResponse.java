package com.sast.sastthread.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
        String username,
        String password,
        String email,
        List<String> roles,
        @JsonProperty("created") LocalDateTime created) {
}
