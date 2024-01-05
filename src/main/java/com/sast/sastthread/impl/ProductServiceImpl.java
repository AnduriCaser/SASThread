package com.sast.sastthread.impl;

import java.nio.file.Path;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sast.sastthread.controller.ProductController;
import com.sast.sastthread.model.Product;
import com.sast.sastthread.repository.ProductRepository;
import com.sast.sastthread.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    @Override
    public void uploadFile(MultipartFile file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uploadFile'");
    }

    @Override
    public void deleteFile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteFile'");
    }

    @Override
    public Resource loadAsResource(String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadAsResource'");
    }

    @Override
    public Path load(String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }

    @Override
    public Product saveAttachment(MultipartFile file) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAttachment'");
    }

    @Override
    public void saveFiles(MultipartFile[] files) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveFiles'");
    }

    @Override
    public List<Product> getAllFiles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllFiles'");
    }

    @Override
    public Page<Product> findBySearchCriteria(Specification<Product> spec, Pageable page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBySearchCriteria'");
    }

    @Override
    public Product getProductBySlug(String slug) {
        return productRepository.findFirstBySlug(slug).get();
    }

    @Override
    public void createProduct(String name, String description, String riskLevel) {
        Product product = new Product(name, description, riskLevel);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void updateProduct(Product product, String name, String description, String riskLevel) {
        product.setName(name);
        product.setDescription(description);
        product.setRisk_level(riskLevel);
        productRepository.save(product);
    }

    @Override
    public Page<Product> listProductsByPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}
