package com.mechyam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmailService emailService;

    @Value("${app.otp.expiry-minutes:10}")
    private int otpExpiryMinutes;

    @Value("${app.otp.length:6}")
    private int otpLength;

    private static final String OTP_PREFIX = "OTP:";

    // =============================
    // Generate & Send OTP
    // =============================
    public String generateOTP(String email) {
        String otp = generateRandomOTP();
        String key = OTP_PREFIX + email;

        redisTemplate.opsForValue().set(
                key,
                otp,
                otpExpiryMinutes,
                TimeUnit.MINUTES
        );

        emailService.sendOTPEmail(email, otp);

        return otp;
    }

    // =============================
    // Validate OTP
    // =============================
    public boolean validateOTP(String email, String otp) {
        String key = OTP_PREFIX + email;
        String storedOtp = redisTemplate.opsForValue().get(key);

        if (storedOtp == null) {
            return false;
        }

        if (storedOtp.equals(otp)) {
            redisTemplate.delete(key); // one-time OTP
            return true;
        }

        return false;
    }

    // =============================
    // Helpers
    // =============================
    private String generateRandomOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}

