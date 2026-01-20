package com.mechyam.service;

import com.mechyam.entity.Admin;
import com.mechyam.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserDetailsService(
            AdminRepository adminRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Admin not found: " + email));

        return User.builder()
                .username(admin.getEmail())
                .password(admin.getPassword()) // BCrypt hash from DB
                .roles(admin.getRole())        // e.g. ADMIN
                .build();
    }

    public boolean validateAdminCredentials(String email, String rawPassword) {
        Admin admin = adminRepository.findByEmail(email).orElse(null);
        return admin != null &&
               passwordEncoder.matches(rawPassword, admin.getPassword());
    }
}

