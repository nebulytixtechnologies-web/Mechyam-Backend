package com.mechyam.service;

import com.mechyam.dto.JobApplicationRequest;
import com.mechyam.entity.Job;
import com.mechyam.entity.JobApplication;
import com.mechyam.repository.JobApplicationRepository;
import com.mechyam.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CareerService {
    
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    
    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private EmailService emailService;
    
    public JobApplication submitJobApplication(JobApplicationRequest applicationRequest) throws IOException {
        // Check if job exists
        Optional<Job> jobOptional = jobRepository.findById(applicationRequest.getJobId());
        if (jobOptional.isEmpty()) {
            throw new RuntimeException("Job not found with id: " + applicationRequest.getJobId());
        }
        
        Job job = jobOptional.get();
        
        // Check if job is active
        if (!job.getIsActive()) {
            throw new RuntimeException("This job is no longer accepting applications");
        }
        
        // Check for duplicate application
        if (jobApplicationRepository.existsByEmailAndJobId(applicationRequest.getEmail(), applicationRequest.getJobId())) {
            throw new RuntimeException("You have already applied for this job");
        }
        
        // Validate resume file
        MultipartFile resumeFile = applicationRequest.getResumeFile();
        if (resumeFile.isEmpty()) {
            throw new RuntimeException("Resume file is required");
        }
        
        // Check file type
        String contentType = resumeFile.getContentType();
        if (contentType == null || (!contentType.equals("application/pdf") && 
            !contentType.equals("application/msword") && 
            !contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
            throw new RuntimeException("Only PDF and Word documents are allowed for resume");
        }
        
        // Check file size (5MB max)
        if (resumeFile.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("Resume file size should not exceed 5MB");
        }
        
        // Store resume file
        String storedFileName = fileStorageService.storeFile(resumeFile);
        
        // Create job application
        JobApplication jobApplication = new JobApplication();
        jobApplication.setJob(job);
        jobApplication.setFullName(applicationRequest.getFullName());
        jobApplication.setEmail(applicationRequest.getEmail());
        jobApplication.setPhoneNumber(applicationRequest.getPhoneNumber());
        jobApplication.setLinkedinUrl(applicationRequest.getLinkedinUrl());
        jobApplication.setPortfolioUrl(applicationRequest.getPortfolioUrl());
        jobApplication.setCoverLetter(applicationRequest.getCoverLetter());
        jobApplication.setResumeFileName(resumeFile.getOriginalFilename());
        jobApplication.setResumeFilePath(storedFileName);
        
        JobApplication savedApplication = jobApplicationRepository.save(jobApplication);
        
        // Send email notification WITH RESUME ATTACHMENT
        try {
            emailService.sendJobApplicationNotification(
                job.getJobTitle(),
                applicationRequest.getFullName(),
                applicationRequest.getEmail(),
                applicationRequest.getPhoneNumber(),
                storedFileName, // Resume file path
                resumeFile.getOriginalFilename() // Original file name
            );
        } catch (Exception e) {
            // Log email failure but don't fail the application submission
            System.err.println("Failed to send email notification: " + e.getMessage());
        }
        
        return savedApplication;
    }
    public Page<JobApplication> getAllApplications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobApplicationRepository.findAllByOrderByApplicationDateDesc(pageable);
    }
    
    public List<JobApplication> getApplicationsByJobId(Long jobId) {
        return jobApplicationRepository.findByJobIdOrderByApplicationDateDesc(jobId);
    }
    
    public List<JobApplication> getApplicationsByStatus(String status) {
        return jobApplicationRepository.findByStatusOrderByApplicationDateDesc(status);
    }
    
    public Optional<JobApplication> getApplicationById(Long id) {
        return jobApplicationRepository.findById(id);
    }
    
    
    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }
    
    public JobApplication updateApplicationStatus(Long id, String status, String notes) {
        Optional<JobApplication> optionalApplication = jobApplicationRepository.findById(id);
        if (optionalApplication.isPresent()) {
            JobApplication application = optionalApplication.get();
            application.setStatus(status);
            application.setNotes(notes);
            return jobApplicationRepository.save(application);
        }
        return null;
    }
}