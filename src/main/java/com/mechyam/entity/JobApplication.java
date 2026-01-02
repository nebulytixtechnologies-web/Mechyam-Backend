package com.mechyam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "JOB_APPLICATIONS")
public class JobApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @ManyToOne
    @JoinColumn(name = "JOB_ID", nullable = false)
    private Job job;
    
    @NotBlank(message = "Full name is required")
    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "EMAIL", nullable = false)
    private String email;
    
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    
    @Column(name = "LINKEDIN_URL")
    private String linkedinUrl;
    
    @Column(name = "PORTFOLIO_URL")
    private String portfolioUrl;
    
    @Column(name = "COVER_LETTER", length = 3000)
    private String coverLetter;
    
    @Column(name = "RESUME_FILE_PATH")
    private String resumeFilePath;
    
    @Column(name = "RESUME_FILE_NAME")
    private String resumeFileName;
    
    @Column(name = "APPLICATION_DATE")
    private LocalDateTime applicationDate;
    
    @Column(name = "STATUS") 
    private String status;
    
    @Column(name = "NOTES", length = 2000)
    private String notes;
    
    // Constructors
    public JobApplication() {
        this.applicationDate = LocalDateTime.now();
        this.status = "SUBMITTED";
    }
    
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    
    public String getPortfolioUrl() { return portfolioUrl; }
    public void setPortfolioUrl(String portfolioUrl) { this.portfolioUrl = portfolioUrl; }
    
    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }
    
    public String getResumeFilePath() { return resumeFilePath; }
    public void setResumeFilePath(String resumeFilePath) { this.resumeFilePath = resumeFilePath; }
    
    public String getResumeFileName() { return resumeFileName; }
    public void setResumeFileName(String resumeFileName) { this.resumeFileName = resumeFileName; }
    
    public LocalDateTime getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
} 