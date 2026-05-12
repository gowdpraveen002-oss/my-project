package com.freshbasket.service;

import com.freshbasket.dto.OrderRequest;
import com.freshbasket.model.Order;
import com.freshbasket.model.OrderItem;
import com.freshbasket.model.Product;
import com.freshbasket.repository.DataStore;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final DataStore store;
    private final ProductService productService;

    public OrderService(DataStore store, ProductService productService) {
        this.store = store;
        this.productService = productService;
    }

    public synchronized Order place(OrderRequest request) {
        Order order = new Order();
        order.setId(store.nextOrderId());
        order.setUserId(request.userId);
        order.setUserEmail(request.userEmail);
        order.setAddress(request.address);
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;
        for (OrderRequest.CartLine line : request.items) {
            Product product = productService.get(line.productId);
            if (product != null && line.quantity > 0) {
                orderItems.add(new OrderItem(product.getId(), product.getName(), line.quantity, product.getPrice()));
                total += product.getPrice() * line.quantity;
            }
        }
        order.setItems(orderItems);
        order.setTotal(total);
        store.getOrders().add(order);
        store.saveAll();
        return order;
    }

    public List<Order> all() { return store.getOrders(); }

    public Order get(Long id) {
        return store.getOrders().stream().filter(o -> o.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Order> byUser(Long userId) {
        return store.getOrders().stream().filter(o -> o.getUserId() != null && o.getUserId().equals(userId)).collect(Collectors.toList());
    }
}
