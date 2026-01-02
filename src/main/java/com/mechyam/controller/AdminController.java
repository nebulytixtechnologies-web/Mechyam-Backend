package com.mechyam.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/dashboard")
    public String getDashboard() {
        return "Welcome to Admin Dashboard";
    }

    // Add your existing admin endpoints here
    // They will be automatically protected by JWT
}