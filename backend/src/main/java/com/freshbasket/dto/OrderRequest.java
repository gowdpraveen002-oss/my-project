package com.freshbasket.dto;

import com.freshbasket.model.Address;
import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    public Long userId;
    public String userEmail;
    public Address address;
    public List<CartLine> items = new ArrayList<>();

    public static class CartLine {
        public Long productId;
        public int quantity;
    }
}
