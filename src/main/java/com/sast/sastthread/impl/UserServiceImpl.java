package com.sast.sastthread.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.sast.sastthread.constant.Constant;
import com.sast.sastthread.dto.UserRequest;
import com.sast.sastthread.dto.UserResponse;
import com.sast.sastthread.errors.ResponseErrorTemplate;
import com.sast.sastthread.exception.CustomMessageException;
import com.sast.sastthread.model.Role;
import com.sast.sastthread.model.User;
import com.sast.sastthread.repository.RoleRepository;
import com.sast.sastthread.repository.UserRepository;
import com.sast.sastthread.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(final UserRepository userRepo, final RoleRepository roleRepo,
            final PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserById(int id) {
        return userRepo.findById(id).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepo.findByRoles(role);
    }

    @Override
    public ResponseErrorTemplate createUser(UserRequest userRequest) {
        this.userRequestValidation(userRequest);

        List<Role> roles = roleRepo.findAllByNameIn(userRequest.roles());
        User user = User.builder()
                .id(0L)
                .username(userRequest.username())
                .password(passwordEncoder.encode(userRequest.password()))
                .email(userRequest.email())
                .roles(new HashSet<>(roles))
                .attempt(0)
                .status(Constant.ACT)
                .createdAt(LocalDateTime.now())
                .build();
        userRepo.save(user);
        return this.userMapper(user);
    }

    @Override
    public ResponseErrorTemplate findById(Long id) {
        Optional<User> user = userRepo.findById(id);
        var msg = String.format(Constant.USER_ID_NOT_FOUND, id);
        return user.map(this::userMapper)
                .orElse(new ResponseErrorTemplate(msg, Constant.USER_NOT_FOUND_CODE, new Object()));
    }

    @Override
    public ResponseErrorTemplate findByUsername(String username) {
        Optional<User> user = userRepo.findFirstByUsername(username);

        var msg = String.format(Constant.USER_NAME_NOT_FOUND, user);
        return user.map(this::userMapper)
                .orElse(new ResponseErrorTemplate(msg, Constant.USER_NOT_FOUND_CODE, new Object()));
    }

    public ResponseErrorTemplate userMapper(User user) {
        UserResponse userResponse = new UserResponse(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getName).toList(),
                user.getCreatedAt());
        return new ResponseErrorTemplate(Constant.SUC_MSG, Constant.SUC_CODE, userResponse);
    }

    private void userRequestValidation(UserRequest userRequest) {

        if (ObjectUtils.isEmpty(userRequest.password())) {
            throw new CustomMessageException("Password can't be blank or null",
                    String.valueOf(HttpStatus.BAD_REQUEST));
        }
        Optional<User> user = userRepo.findFirstByUsernameOrEmail(userRequest.username(), userRequest.email());
        if (user.isPresent()) {
            throw new CustomMessageException("Username or Email already exists.",
                    String.valueOf(HttpStatus.BAD_REQUEST));
        }

        List<String> roles = roleRepo.findAll().stream().map(Role::getName).toList();
        for (var role : userRequest.roles()) {
            if (!roles.contains(role)) {
                throw new CustomMessageException("Role is invalid request.",
                        String.valueOf(HttpStatus.BAD_REQUEST));
            }
        }
    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword) {
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(newPassword);
            userRepo.save(user);
        }

    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    @Override
    public boolean isUsernameUnique(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isUsernameUnique'");
    }

    @Override
    public boolean isEmailUnique(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmailUnique'");
    }

    @Override
    public void updateUser(User user, String username, String email, String password) {
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
    }

    @Override
    public void activateUser(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'activateUser'");
    }

    @Override
    public void deactivateUser(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deactivateUser'");
    }

    @Override
    public List<String> getUserRoles(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserRoles'");
    }

    @Override
    public boolean isUserAccountLocked(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isUserAccountLocked'");
    }

    @Override
    public void updateLastLogin(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateLastLogin'");
    }

    @Override
    public boolean isUserOnline(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isUserOnline'");
    }

    @Override
    public void inviteUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inviteUser'");
    }

    @Override
    public void inviteUserToProject(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inviteUserToProject'");
    }

    @Override
    public boolean isUserInRole(int userId, String role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isUserInRole'");
    }

    @Override
    public Page<User> findBySearchCriteria(Specification<User> spec, Pageable page) {
        Page<User> searchResult = userRepo.findAll(spec, page);
        return searchResult;
    }

    @Override
    public User getUserByName(String username) {
        return userRepo.findFirstByUsername(username).get();
    }
}
