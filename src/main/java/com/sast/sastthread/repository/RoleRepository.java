package com.sast.sastthread.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sast.sastthread.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findFirstBySlug(String slug);

    List<Role> findAllByNameIn(List<String> names);

    Optional<Role> findById(Long id);

    List<Role> findAll();
}
