package com.mechyam.dto;

public class LoginResponse {
    private String token;
    private String email;
    private String message;

    public LoginResponse() {}
    
    public LoginResponse(String token, String email, String message) {
        this.token = token;
        this.email = email;
        this.message = message;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}