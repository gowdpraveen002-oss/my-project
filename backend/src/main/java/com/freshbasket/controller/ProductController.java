package com.freshbasket.controller;

import com.freshbasket.model.Product;
import com.freshbasket.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) { this.productService = productService; }

    @GetMapping
    public List<Product> all() { return productService.all(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Product product = productService.get(id);
        if (product == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestParam String role,
                                 @RequestParam String name,
                                 @RequestParam String category,
                                 @RequestParam String description,
                                 @RequestParam double price,
                                 @RequestParam double oldPrice,
                                 @RequestParam String unit,
                                 @RequestParam int stock,
                                 @RequestParam(required = false) MultipartFile image) {
        if (!"ADMIN".equalsIgnoreCase(role)) return ResponseEntity.status(403).body(Map.of("message", "Only admin can add products"));
        return ResponseEntity.ok(productService.add(name, category, description, price, oldPrice, unit, stock, image));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestParam String role,
                                    @RequestParam String name,
                                    @RequestParam String category,
                                    @RequestParam String description,
                                    @RequestParam double price,
                                    @RequestParam double oldPrice,
                                    @RequestParam String unit,
                                    @RequestParam int stock,
                                    @RequestParam(required = false) MultipartFile image) {
        if (!"ADMIN".equalsIgnoreCase(role)) return ResponseEntity.status(403).body(Map.of("message", "Only admin can update products"));
        Product product = productService.update(id, name, category, description, price, oldPrice, unit, stock, image);
        if (product == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) return ResponseEntity.status(403).body(Map.of("message", "Only admin can delete products"));
        return ResponseEntity.ok(Map.of("deleted", productService.delete(id)));
    }
}
