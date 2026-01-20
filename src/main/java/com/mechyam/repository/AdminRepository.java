package com.mechyam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mechyam.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    boolean existsByEmail(String email);
}

