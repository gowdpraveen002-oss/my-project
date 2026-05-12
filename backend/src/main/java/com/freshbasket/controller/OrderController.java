package com.freshbasket.controller;

import com.freshbasket.dto.OrderRequest;
import com.freshbasket.model.Order;
import com.freshbasket.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) { this.orderService = orderService; }

    @PostMapping
    public ResponseEntity<?> place(@RequestBody OrderRequest request) {
        if (request.items == null || request.items.isEmpty()) return ResponseEntity.badRequest().body(Map.of("message", "Cart is empty"));
        return ResponseEntity.ok(orderService.place(request));
    }

    @GetMapping
    public ResponseEntity<?> all(@RequestParam String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) return ResponseEntity.status(403).body(Map.of("message", "Only admin can view all orders"));
        return ResponseEntity.ok(orderService.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, @RequestParam(required = false) String role, @RequestParam(required = false) Long userId) {
        Order order = orderService.get(id);
        if (order == null) return ResponseEntity.notFound().build();
        if ("ADMIN".equalsIgnoreCase(role) || (userId != null && userId.equals(order.getUserId()))) return ResponseEntity.ok(order);
        return ResponseEntity.status(403).body(Map.of("message", "Access denied"));
    }

    @GetMapping("/user/{userId}")
    public List<Order> byUser(@PathVariable Long userId) { return orderService.byUser(userId); }
}
