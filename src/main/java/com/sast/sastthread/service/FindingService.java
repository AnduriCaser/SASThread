package com.sast.sastthread.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.sast.sastthread.model.Finding;

public interface FindingService {
    Finding getFindingBySlug(String slug);

    Page<Finding> listFindingsByPage(Pageable pageable);

    void createFinding(String title, String severity, String vulnerableCode, List<String> endpoints);

    void deleteFinding(Finding finding);

    void updateFinding(Finding finding, String title, String severity, String vulnerableCode, List<String> endpoints);

    Page<Finding> findBySearchCriteria(Specification<Finding> spec, Pageable page);
}
