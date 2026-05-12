package com.freshbasket.service;

import com.freshbasket.model.User;
import com.freshbasket.repository.DataStore;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthService {
    private final DataStore store;
    private final Map<String, String> otpMap = new HashMap<>();
    private final Random random = new Random();

    public AuthService(DataStore store) { this.store = store; }

    public synchronized String sendOtp(String email) {
        String finalEmail = normalize(email);
        if (findByEmail(finalEmail) != null) return "EMAIL_EXISTS";
        String otp = String.valueOf(100000 + random.nextInt(900000));
        otpMap.put(finalEmail, otp);
        System.out.println("========================================");
        System.out.println("FreshBasket OTP for " + finalEmail + " : " + otp);
        System.out.println("========================================");
        return "OTP_SENT";
    }

    public synchronized User verifyAndRegister(String name, String email, String password, String otp) {
        String finalEmail = normalize(email);
        String savedOtp = otpMap.get(finalEmail);
        if (savedOtp == null || !savedOtp.equals(otp)) return null;
        if (findByEmail(finalEmail) != null) return null;
        User user = new User(store.nextUserId(), name, finalEmail, password, "USER");
        store.getUsers().add(user);
        otpMap.remove(finalEmail);
        store.saveAll();
        return user;
    }

    public synchronized User login(String email, String password) {
        String finalEmail = normalize(email);
        for (User user : store.getUsers()) {
            if (user.getEmail() != null && user.getEmail().equalsIgnoreCase(finalEmail)
                    && user.getPassword() != null && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private User findByEmail(String email) {
        String finalEmail = normalize(email);
        for (User user : store.getUsers()) {
            if (user.getEmail() != null && user.getEmail().equalsIgnoreCase(finalEmail)) return user;
        }
        return null;
    }

    private String normalize(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }
}
