package com.mechyam.service;

import com.mechyam.entity.Admin;
import com.mechyam.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Used by Spring Security internally
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Admin user not found with email: " + email)
                );

        return new User(
                admin.getEmail(),
                admin.getPassword(), // BCrypt hash from DB
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + admin.getRole())
                )
        );
    }

    /**
     * Used by your AdminAuthController for login validation
     */
    public boolean validateAdminCredentials(String email, String rawPassword) {

        Admin admin = adminRepository.findByEmail(email).orElse(null);

        if (admin == null) {
            return false;
        }

        // BCrypt password comparison
        return passwordEncoder.matches(rawPassword, admin.getPassword());
    }
}

