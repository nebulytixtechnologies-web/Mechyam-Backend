package com.mechyam.dto;

public class OTPResponse {
    private boolean success;
    private String message;
    private String tempToken;

    public OTPResponse() {}
    
    public OTPResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public OTPResponse(boolean success, String message, String tempToken) {
        this.success = success;
        this.message = message;
        this.tempToken = tempToken;
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getTempToken() { return tempToken; }
    public void setTempToken(String tempToken) { this.tempToken = tempToken; }
}