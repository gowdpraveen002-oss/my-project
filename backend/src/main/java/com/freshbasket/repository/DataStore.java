package com.freshbasket.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.freshbasket.model.Order;
import com.freshbasket.model.Product;
import com.freshbasket.model.User;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataStore {
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final File dataDir = new File("data");
    private final File usersFile = new File(dataDir, "users.json");
    private final File productsFile = new File(dataDir, "products.json");
    private final File ordersFile = new File(dataDir, "orders.json");

    private final List<User> users = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();

    public DataStore() {
        loadAll();
        seedAdmin();
        seedProducts();
        saveAll();
    }

    public synchronized List<User> getUsers() { return users; }
    public synchronized List<Product> getProducts() { return products; }
    public synchronized List<Order> getOrders() { return orders; }

    public synchronized Long nextUserId() { return users.stream().mapToLong(User::getId).max().orElse(0L) + 1; }
    public synchronized Long nextProductId() { return products.stream().mapToLong(Product::getId).max().orElse(0L) + 1; }
    public synchronized Long nextOrderId() { return orders.stream().mapToLong(Order::getId).max().orElse(0L) + 1; }

    public synchronized void saveAll() {
        try {
            if (!dataDir.exists()) dataDir.mkdirs();
            mapper.writerWithDefaultPrettyPrinter().writeValue(usersFile, users);
            mapper.writerWithDefaultPrettyPrinter().writeValue(productsFile, products);
            mapper.writerWithDefaultPrettyPrinter().writeValue(ordersFile, orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save data", e);
        }
    }

    private void loadAll() {
        try {
            if (!dataDir.exists()) dataDir.mkdirs();
            if (usersFile.exists()) users.addAll(mapper.readValue(usersFile, new TypeReference<List<User>>() {}));
            if (productsFile.exists()) products.addAll(mapper.readValue(productsFile, new TypeReference<List<Product>>() {}));
            if (ordersFile.exists()) orders.addAll(mapper.readValue(ordersFile, new TypeReference<List<Order>>() {}));
        } catch (Exception e) {
            System.out.println("Could not load old data. Starting clean. Reason: " + e.getMessage());
        }
    }

    private void seedAdmin() {
        boolean adminExists = users.stream().anyMatch(u -> "admin@freshbasket.com".equalsIgnoreCase(u.getEmail()));
        if (!adminExists) {
            users.add(new User(1L, "Admin", "admin@freshbasket.com", "admin123", "ADMIN"));
        }
    }

    private void seedProducts() {
        if (!products.isEmpty()) return;
        products.add(new Product(1L, "Tomato", "Vegetables", "Fresh red tomatoes", 40.0, 60.0, "1 kg", 50, ""));
        products.add(new Product(2L, "Potato", "Vegetables", "Fresh potatoes", 30.0, 50.0, "1 kg", 60, ""));
        products.add(new Product(3L, "Onion", "Vegetables", "Fresh onions", 45.0, 70.0, "1 kg", 70, ""));
        products.add(new Product(4L, "Spinach", "Leafy", "Fresh spinach bunch", 25.0, 40.0, "1 bunch", 40, ""));
    }
}
