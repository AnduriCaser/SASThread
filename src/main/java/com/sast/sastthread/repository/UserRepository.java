package com.sast.sastthread.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sast.sastthread.model.Role;
import com.sast.sastthread.model.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findById(int id);

    Optional<User> findFirstBySlug(String slug);

    Optional<User> findFirstByUsername(String username);

    Optional<User> findByUsernameLike(String username);

    Optional<User> findFirstByUsernameOrEmail(String username, String email);

    List<User> findByUsernameStartsWith(String username);

    List<User> findByUsernameEndsWith(String username);

    List<User> findByActive(Boolean active);

    List<User> findByUsernameOrEmail(String username, String email);

    List<User> findByRoles(Role role);

    List<User> findAll();
}
