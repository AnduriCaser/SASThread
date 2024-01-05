package com.sast.sastthread.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import com.sast.sastthread.model.Product;

public interface ProductService extends FileService {

    Product getProductBySlug(String slug);

    Product saveAttachment(MultipartFile file) throws Exception;

    void saveFiles(MultipartFile[] files) throws Exception;

    List<Product> getAllFiles();

    Page<Product> findBySearchCriteria(Specification<Product> spec, Pageable page);

    void createProduct(String name, String description, String riskLevel);

    void deleteProduct(Product product);

    void updateProduct(Product product, String name, String description, String riskLevel);

    Page<Product> listProductsByPage(Pageable pageable);
}