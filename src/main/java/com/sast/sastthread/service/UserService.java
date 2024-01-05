package com.sast.sastthread.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.sast.sastthread.dto.UserRequest;
import com.sast.sastthread.errors.ResponseErrorTemplate;
import com.sast.sastthread.model.Role;
import com.sast.sastthread.model.User;

public interface UserService {

    ResponseErrorTemplate findById(Long id);

    ResponseErrorTemplate findByUsername(String username);

    User getUserByName(String username);

    User getUserById(int id);

    List<User> getAllUsers();

    List<User> getUsersByRole(Role role);

    ResponseErrorTemplate createUser(UserRequest userRequest);

    void changePassword(User user, String oldPassword, String newPassword);

    void deleteUser(User user);

    boolean isUsernameUnique(String username);

    boolean isEmailUnique(String email);

    void updateUser(User user ,String username, String email, String password);

    boolean isUserInRole(int userId, String role);

    void activateUser(int userId);

    void deactivateUser(int userId);

    List<String> getUserRoles(int userId);

    boolean isUserAccountLocked(int userId);

    void updateLastLogin(int userId);

    boolean isUserOnline(int userId);

    void inviteUser(User user);

    void inviteUserToProject(User user);

    Page<User> findBySearchCriteria(Specification<User> spec, Pageable page);

}
