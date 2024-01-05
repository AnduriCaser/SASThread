package com.sast.sastthread.controller;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sast.sastthread.model.Finding;
import com.sast.sastthread.model.Product;
import com.sast.sastthread.repository.FindingRepository;
import com.sast.sastthread.repository.ProductRepository;
import com.sast.sastthread.sast.SastService;
import com.sast.sastthread.service.ProductService;
import com.sast.sastthread.validation.FileValidatorService;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/v1/product/{slug}/files")
public class ProductFilesController {

    private final FileValidatorService fileValidatorService;
    private final SastService sastService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final FindingRepository findingRepository;

    public ProductFilesController(final FileValidatorService fileValidatorService, final SastService sastService,
            ProductService productService, ProductRepository productRepository, FindingRepository findingRepository) {
        this.fileValidatorService = fileValidatorService;
        this.productService = productService;
        this.findingRepository = findingRepository;
        this.sastService = sastService;
        this.productRepository = productRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> fileUpload(Principal principal, @PathVariable String slug,
            @RequestParam("file") MultipartFile file) {
        try {
            String loggedInUser = principal.getName();
            if (fileValidatorService.validate(file)) {
                JsonNode jsonNode = sastService.process(file);
                Product product = productService.getProductBySlug(slug);
                Finding finding = new Finding();

                finding.setTitle(jsonNode.get("title").asText());
                finding.setSeverity(jsonNode.get("severity").asText());
                ArrayNode endpointsNode = (ArrayNode) jsonNode.get("endpoints");
                List<String> endpoints = new ArrayList<>();
                endpointsNode.forEach(endpoint -> endpoints.add(endpoint.asText()));

                finding.setEndpoints(endpoints);

                product.getFindings().add(finding);

                productRepository.save(product);
                findingRepository.save(finding);

                return ResponseEntity.ok("File uploaded successfully.");
            }
            return ResponseEntity.ok("File not accepted !");

        } catch (IOException e) {
            ResponseEntity.ok("Something went wrong !");
        }
        return null;
    }
}
