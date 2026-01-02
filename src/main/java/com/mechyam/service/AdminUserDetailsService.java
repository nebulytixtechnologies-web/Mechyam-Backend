package com.mechyam.service;

import com.mechyam.entity.Admin;
import com.mechyam.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Admin not found: " + email));

        return new User(
                admin.getEmail(),
                admin.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + admin.getRole())
                )
        );
    }

    public boolean validateAdminCredentials(String email, String rawPassword) {
        Admin admin = adminRepository.findByEmail(email).orElse(null);
        if (admin == null) return false;

        return passwordEncoder.matches(rawPassword, admin.getPassword());
    }
}

