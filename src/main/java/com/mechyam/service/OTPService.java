package com.mechyam.service;

import java.time.Duration;
import java.util.Random;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OTPService {

    private final RedisTemplate<String, String> redisTemplate;

    public OTPService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // =============================
    // Generate & Store OTP
    // =============================
    public String generateOTP(String email) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        redisTemplate.opsForValue().set(
                "OTP:" + email,
                otp,
                Duration.ofMinutes(10)
        );

        return otp;
    }

    // =============================
    // Validate OTP
    // =============================
    public boolean validateOTP(String email, String otp) {
        String key = "OTP:" + email;
        String storedOtp = redisTemplate.opsForValue().get(key);

        if (storedOtp == null) {
            return false;
        }

        boolean isValid = storedOtp.equals(otp);

        if (isValid) {
            redisTemplate.delete(key);
        }

        return isValid;
    }
}
