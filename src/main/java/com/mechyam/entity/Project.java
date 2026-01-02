package com.mechyam.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
public class Project {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @Column(nullable = false)
 private String title;
 
 @Column(length = 1000)
 private String description;
 
 @Column(name = "image_url")
 private String imageUrl;
 
 @Column(name = "cloudinary_public_id")
 private String cloudinaryPublicId;
 
 @Column(name = "created_at")
 private LocalDateTime createdAt;
 
 @Column(name = "updated_at")
 private LocalDateTime updatedAt;
 
 // PrePersist and PreUpdate methods
 @PrePersist
 protected void onCreate() {
     createdAt = LocalDateTime.now();
     updatedAt = LocalDateTime.now();
 }
 
 @PreUpdate
 protected void onUpdate() {
     updatedAt = LocalDateTime.now();
 }
 
 // Constructors
 public Project() {}
 
 public Project(String title, String description, String imageUrl, String cloudinaryPublicId) {
     this.title = title;
     this.description = description;
     this.imageUrl = imageUrl;
     this.cloudinaryPublicId = cloudinaryPublicId;
 }
 
 // Getters and Setters
 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }
 
 public String getTitle() { return title; }
 public void setTitle(String title) { this.title = title; }
 
 public String getDescription() { return description; }
 public void setDescription(String description) { this.description = description; }
 
 public String getImageUrl() { return imageUrl; }
 public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
 
 public String getCloudinaryPublicId() { return cloudinaryPublicId; }
 public void setCloudinaryPublicId(String cloudinaryPublicId) { this.cloudinaryPublicId = cloudinaryPublicId; }
 
 public LocalDateTime getCreatedAt() { return createdAt; }
 public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
 
 public LocalDateTime getUpdatedAt() { return updatedAt; }
 public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}