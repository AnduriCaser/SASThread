package com.sast.sastthread.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sast.sastthread.dto.UserRequest;
import com.sast.sastthread.errors.ResponseErrorTemplate;
import com.sast.sastthread.model.User;
import com.sast.sastthread.service.AuthService;
import com.sast.sastthread.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {


    private final AuthService authService;
    private final UserService userService;

    public AuthController(final AuthService authService, final UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(Principal principal) {
        String loggedInUser = principal.getName();
        ResponseErrorTemplate user = userService.findByUsername(loggedInUser);
        authService.logout(loggedInUser);
        return ResponseEntity.ok("User logout successfully.");
    }

}
