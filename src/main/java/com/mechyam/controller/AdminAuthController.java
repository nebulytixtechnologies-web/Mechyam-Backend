package com.mechyam.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.mechyam.dto.*;
import com.mechyam.service.AdminUserDetailsService;
import com.mechyam.service.EmailService;
import com.mechyam.service.OTPService;
import com.mechyam.util.JwtUtil;

@RestController
@RequestMapping("/api/admin/auth")
@CrossOrigin(origins = "*")
public class AdminAuthController {

    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    //@Autowired
   // private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/test")
    public String test() {
        return "Admin Auth API is working!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            boolean isValid = adminUserDetailsService.validateAdminCredentials(
                loginRequest.getEmail(), loginRequest.getPassword()
            );

            if (isValid) {
                String otp = otpService.generateOTP(loginRequest.getEmail());
                String tempToken = jwtUtil.generateTempToken(loginRequest.getEmail());

                // Send OTP Email
                emailService.sendOTPEmail(loginRequest.getEmail(), otp);

                return ResponseEntity.ok(
                    new OTPResponse(true, "OTP sent to your email. Please verify.", tempToken)
                );
            } else {
                return ResponseEntity.status(401).body(new OTPResponse(false, "Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new OTPResponse(false, "Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody OTPRequest otpRequest,
                                       @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401)
                    .body(new LoginResponse(null, null, "Temporary token required"));
            }

            String tempToken = authHeader.substring(7);

            if (!jwtUtil.validateToken(tempToken) || !jwtUtil.isTempToken(tempToken)) {
                return ResponseEntity.status(401)
                    .body(new LoginResponse(null, null, "Invalid or expired temporary token"));
            }

            String emailFromToken = jwtUtil.getUsernameFromToken(tempToken);
            if (!emailFromToken.equals(otpRequest.getEmail())) {
                return ResponseEntity.status(401)
                    .body(new LoginResponse(null, null, "Email mismatch"));
            }

            boolean isOtpValid = otpService.validateOTP(otpRequest.getEmail(), otpRequest.getOtp());

            if (isOtpValid) {
                String finalToken = jwtUtil.generateToken(otpRequest.getEmail());
                return ResponseEntity.ok(new LoginResponse(finalToken, otpRequest.getEmail(), "Login successful"));
            } else {
                return ResponseEntity.status(401)
                    .body(new LoginResponse(null, null, "Invalid OTP"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new LoginResponse(null, null, "OTP verification failed: " + e.getMessage()));
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body("Invalid token");
            }

            if (jwtUtil.isTempToken(token)) {
                return ResponseEntity.status(401).body("Temporary token. OTP not verified.");
            }

            return ResponseEntity.ok("Valid token");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token validation failed");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logout successful");
    }
}
