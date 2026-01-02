package com.mechyam.dto;

public class OTPRequest {
    private String email;
    private String otp;

    // Constructors
    public OTPRequest() {}
    
    public OTPRequest(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
}