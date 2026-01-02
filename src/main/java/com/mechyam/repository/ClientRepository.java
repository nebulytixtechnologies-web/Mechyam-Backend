package com.mechyam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mechyam.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}

