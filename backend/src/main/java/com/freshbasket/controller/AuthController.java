package com.freshbasket.controller;

import com.freshbasket.dto.AuthDtos;
import com.freshbasket.model.User;
import com.freshbasket.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody AuthDtos.SignupRequest request) {
        String result = authService.sendOtp(request.email);
        if ("EMAIL_EXISTS".equals(result)) return ResponseEntity.badRequest().body(Map.of("message", "Email already registered"));
        return ResponseEntity.ok(Map.of("message", "OTP generated. Check backend terminal."));
    }

    @PostMapping("/verify-register")
    public ResponseEntity<?> verifyRegister(@RequestBody AuthDtos.VerifyRequest request) {
        User user = authService.verifyAndRegister(request.name, request.email, request.password, request.otp);
        if (user == null) return ResponseEntity.badRequest().body(Map.of("message", "Invalid OTP or email already exists"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDtos.LoginRequest request) {
        User user = authService.login(request.email, request.password);
        if (user == null) return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        return ResponseEntity.ok(user);
    }
}
