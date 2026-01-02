package com.mechyam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "CONTACT_SUBMISSIONS")
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "EMAIL", nullable = false)
    private String email;
    
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    
    @Column(name = "SERVICE_TYPE")
    private String serviceType;
    
    @Column(name = "MESSAGE", length = 4000)
    private String message;
    
    @Column(name = "CAPTCHA_ANSWER")
    private Integer captchaAnswer;
    
    @Column(name = "SUBMISSION_DATE")
    private LocalDateTime submissionDate;
    
    @Column(name = "STATUS")
    private String status;
    
    // Constructors
    public Contact() {
        this.submissionDate = LocalDateTime.now();
        this.status = "NEW";
    }
    
    public Contact(String name, String email, String phoneNumber, String serviceType, String message, Integer captchaAnswer) {
        this();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.serviceType = serviceType;
        this.message = message;
        this.captchaAnswer = captchaAnswer;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Integer getCaptchaAnswer() { return captchaAnswer; }
    public void setCaptchaAnswer(Integer captchaAnswer) { this.captchaAnswer = captchaAnswer; }
    
    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}