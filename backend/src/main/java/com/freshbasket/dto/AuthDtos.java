package com.freshbasket.dto;

public class AuthDtos {
    public static class SignupRequest {
        public String name;
        public String email;
        public String password;
    }
    public static class VerifyRequest {
        public String name;
        public String email;
        public String password;
        public String otp;
    }
    public static class LoginRequest {
        public String email;
        public String password;
    }
}
