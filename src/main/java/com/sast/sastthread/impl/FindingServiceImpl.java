package com.sast.sastthread.impl;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sast.sastthread.model.Finding;
import com.sast.sastthread.repository.FindingRepository;
import com.sast.sastthread.service.FindingService;

import lombok.RequiredArgsConstructor;

@Service
public class FindingServiceImpl implements FindingService {

    private final FindingRepository findingRepo;

    public FindingServiceImpl(final FindingRepository findingRepo){
        this.findingRepo = findingRepo;
    }

    public Page<Finding> findBySearchCriteria(Specification<Finding> spec, Pageable page) {
        Page<Finding> searchResult = findingRepo.findAll(spec, page);
        return searchResult;
    }

    @Override
    public void createFinding(String title, String severity, String vulnerableCode, List<String> endpoints) {
        Finding finding = new Finding(title, severity, endpoints);
        findingRepo.save(finding);
    }

    @Override
    public void deleteFinding(Finding finding) {
        findingRepo.delete(finding);
    }

    @Override
    public void updateFinding(Finding finding, String title, String severity, String vulnerableCode,
            List<String> endpoints) {
        finding.setTitle(title);
        finding.setSeverity(severity);
        finding.setEndpoints(endpoints);
    }

    @Override
    public Finding getFindingBySlug(String slug) {
        return findingRepo.findFirstBySlug(slug).get();
    }

    @Override
    public Page<Finding> listFindingsByPage(Pageable pageable) {
        return findingRepo.findAll(pageable);
    }

}
