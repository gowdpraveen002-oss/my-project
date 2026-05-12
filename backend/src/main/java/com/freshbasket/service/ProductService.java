package com.freshbasket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.freshbasket.model.Product;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    public ProductService() {

        products.add(
                new Product(
                        1L,
                        "Tomato",
                        "Fresh tomatoes",
                        "Vegetables",
                        40.0,
                        50.0,
                        "tomato.jpg",
                        10,
                        "1 KG"
                )
        );

        products.add(
                new Product(
                        2L,
                        "Potato",
                        "Fresh potatoes",
                        "Vegetables",
                        30.0,
                        40.0,
                        "potato.jpg",
                        15,
                        "1 KG"
                )
        );
    }

    // GET ALL
    public List<Product> all() {
        return products;
    }

    // GET ONE
    public Product get(Long id) {

        for (Product product : products) {

            if (product.getId().equals(id)) {
                return product;
            }
        }

        return null;
    }

    // ADD PRODUCT
    public Product add(String name,
                       String description,
                       String category,
                       double price,
                       double oldPrice,
                       String unit,
                       int quantity,
                       MultipartFile image) {

        String imageName = image != null ? image.getOriginalFilename() : "";

        Product product = new Product(
                (long) (products.size() + 1),
                name,
                description,
                category,
                price,
                oldPrice,
                imageName,
                quantity,
                unit
        );

        products.add(product);

        return product;
    }

    // UPDATE PRODUCT
    public Product update(Long id,
                          String name,
                          String description,
                          String category,
                          double price,
                          double oldPrice,
                          String unit,
                          int quantity,
                          MultipartFile image) {

        Product product = get(id);

        if (product == null) {
            return null;
        }

        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product.setOldPrice(oldPrice);
        product.setUnit(unit);
        product.setQuantity(quantity);

        if (image != null) {
            product.setImageUrl(image.getOriginalFilename());
        }

        return product;
    }

    // DELETE PRODUCT
    public boolean delete(Long id) {

        return products.removeIf(
                product -> product.getId().equals(id)
        );
    }
}