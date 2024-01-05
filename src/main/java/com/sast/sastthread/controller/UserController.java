package com.sast.sastthread.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sast.sastthread.model.User;
import com.sast.sastthread.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(Principal principal, @PathVariable String slug,
            @RequestBody Map<String, String> updateProfile) {
        String loggedInUsername = principal.getName();

        User user = userService.getUserByName(loggedInUsername);

        String username = updateProfile.get("username");
        String email = updateProfile.get("email");
        String password = updateProfile.get("password");

        userService.updateUser(user, username, email, password);

        return ResponseEntity.ok("Profile updated successfully");
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(Principal principal, @PathVariable String slug,
            @RequestBody Map<String, String> changePassword) {

        String loggedInUsername = principal.getName();
        String oldPassword = changePassword.get("oldPassword");
        String newPassword = changePassword.get("newPassword");

        User user = userService.getUserByName(loggedInUsername);

        userService.changePassword(user, oldPassword, newPassword);

        return ResponseEntity.ok("Password changed successfully");
    }

    @GetMapping("/delete/account")
    public ResponseEntity<String> deleteAccount(Principal principal) {

        String loggedInUsername = principal.getName();

        User user = userService.getUserByName(loggedInUsername);

        userService.deleteUser(user);

        return ResponseEntity.ok("Account deleted successfully");
    }
}
