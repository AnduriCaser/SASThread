package com.sast.sastthread.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequest(
        String username,
        String password,
        String email,
        @JsonProperty("roles") List<String> roles) {
}
