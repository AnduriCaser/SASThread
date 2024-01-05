package com.sast.sastthread.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sast.sastthread.model.Finding;

public interface FindingRepository extends JpaRepository<Finding, Long>, JpaSpecificationExecutor<Finding> {
    Optional<Finding> findFirstBySlug(String slug);

    Optional<Finding> findById(Long id);

    List<Finding> findAll();
}
