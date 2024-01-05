package com.sast.sastthread.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sast.sastthread.model.Finding;
import com.sast.sastthread.model.Product;
import com.sast.sastthread.model.User;
import com.sast.sastthread.service.FindingService;
import com.sast.sastthread.service.ProductService;
import com.sast.sastthread.service.UserService;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "api/v1/product/{slug}/findings")
public class ProductFindingsController {

    private final FindingService findingsService;
    private final ProductService productService;
    private final UserService userService;

    public ProductFindingsController(final FindingService findingService, final ProductService productService,
            final UserService userService) {
        this.findingsService = findingService;
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/{slugFinding}/delete")
    public ResponseEntity<String> deleteFinding(@PathVariable String slug, @PathVariable String slugFinding,
            Principal principal,
            @RequestBody Map<String, String> deleteFinding) {
        String loggedInUsername = principal.getName();

        User user = userService.getUserByName(loggedInUsername);

        Product product = productService.getProductBySlug(slug);

        Finding finding = findingsService.getFindingBySlug(slugFinding);

        findingsService.deleteFinding(finding);

        return ResponseEntity.ok("Profile updated successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Finding>> listFindingsByPage(Principal principal, @PathVariable String slug,
            @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
            @RequestBody Map<String, String> list) {
        String loggedInUsername = principal.getName();

        User user = userService.getUserByName(loggedInUsername);

        Product product = productService.getProductBySlug(slug);

        Pageable pageable = PageRequest.of(pageNum, pageSize);

        List<Finding> findings = findingsService.listFindingsByPage(pageable).toList();

        return ResponseEntity.ok(findings);
    }

}
