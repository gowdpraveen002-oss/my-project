package com.freshbasket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.freshbasket.model.Product;

@Service
public class StoreService {

    private final List<Product> products = new ArrayList<>();

    public StoreService() {

        products.add(
                new Product(
                        1L,
                        "Tomato",
                        "Fresh tomatoes directly from farms",
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
                        "Fresh potatoes directly from farms",
                        "Vegetables",
                        30.0,
                        40.0,
                        "potato.jpg",
                        15,
                        "1 KG"
                )
        );

        products.add(
                new Product(
                        3L,
                        "Onion",
                        "Fresh onions directly from farms",
                        "Vegetables",
                        50.0,
                        60.0,
                        "onion.jpg",
                        20,
                        "1 KG"
                )
        );

        products.add(
                new Product(
                        4L,
                        "Carrot",
                        "Organic carrots",
                        "Vegetables",
                        60.0,
                        75.0,
                        "carrot.jpg",
                        12,
                        "500 G"
                )
        );
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product addProduct(Product product) {

        product.setId((long) (products.size() + 1));

        products.add(product);

        return product;
    }

    public Product updateProduct(Long id, Product updatedProduct) {

        for (Product product : products) {

            if (product.getId().equals(id)) {

                product.setName(updatedProduct.getName());
                product.setDescription(updatedProduct.getDescription());
                product.setCategory(updatedProduct.getCategory());
                product.setPrice(updatedProduct.getPrice());
                product.setOldPrice(updatedProduct.getOldPrice());
                product.setImageUrl(updatedProduct.getImageUrl());
                product.setQuantity(updatedProduct.getQuantity());
                product.setUnit(updatedProduct.getUnit());

                return product;
            }
        }

        return null;
    }

    public boolean deleteProduct(Long id) {

        return products.removeIf(product -> product.getId().equals(id));
    }
}