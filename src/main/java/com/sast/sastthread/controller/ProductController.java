package com.sast.sastthread.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sast.sastthread.model.Product;
import com.sast.sastthread.model.User;
import com.sast.sastthread.service.ProductService;
import com.sast.sastthread.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    public ProductController(final ProductService productService, final UserService userService) {
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(Principal principal, @RequestBody Map<String, String> createProduct) {
        String loggedInUsername = principal.getName();

        User user = userService.getUserByName(loggedInUsername);

        String name = createProduct.get("name");
        String description = createProduct.get("description");
        String riskLevel = createProduct.get("riskLevel");

        productService.createProduct(name, description, riskLevel);

        return ResponseEntity.ok("Product created successfully");
    }

    @PostMapping("/{slug}/update")
    public ResponseEntity<String> updateProduct(Principal principal, @PathVariable String slug,
            @RequestBody Map<String, String> updateProduct) {
        String loggedInUsername = principal.getName();

        String name = updateProduct.get("name");
        String description = updateProduct.get("description");
        String riskLevel = updateProduct.get("riskLevel");

        User user = userService.getUserByName(loggedInUsername);

        Product product = productService.getProductBySlug(slug);

        productService.updateProduct(product, name, description, riskLevel);

        return ResponseEntity.ok("Product updated successfully");
    }

    @PostMapping("/{slug}/delete")
    public ResponseEntity<String> deleteProduct(Principal principal, @PathVariable String slug,
            @RequestBody Map<String, String> deleteProduct) {
        String loggedInUsername = principal.getName();

        User user = userService.getUserByName(loggedInUsername);

        Product product = productService.getProductBySlug(slug);

        productService.deleteProduct(product);

        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> listProductsByPage(Principal principal,
            @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        String loggedInUsername = principal.getName();

        User user = userService.getUserByName(loggedInUsername);

        Pageable pageable = PageRequest.of(pageNum, pageSize);

        List<Product> products = productService.listProductsByPage(pageable).toList();

        return ResponseEntity.ok(products);
    }
}
