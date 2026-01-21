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
    private JobRepository jobRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private EmailService emailService;

    /**
     * SUBMIT JOB APPLICATION
     * (This is your original working logic – unchanged)
     */
    public JobApplication submitJobApplication(JobApplicationRequest applicationRequest)
            throws IOException {

        // 1. Validate Job
        Job job = jobRepository.findById(applicationRequest.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getIsActive()) {
            throw new RuntimeException("This job is no longer accepting applications");
        }

        if (jobApplicationRepository.existsByEmailAndJobId(
                applicationRequest.getEmail(),
                applicationRequest.getJobId())) {
            throw new RuntimeException("You have already applied for this job");
        }

        // 2. Validate Resume
        MultipartFile resumeFile = applicationRequest.getResumeFile();

        if (resumeFile == null || resumeFile.isEmpty()) {
            throw new RuntimeException("Resume file is required");
        }

        // PDF only
        if (!"application/pdf".equalsIgnoreCase(resumeFile.getContentType())) {
            throw new RuntimeException("Only PDF resumes are allowed");
        }

        if (resumeFile.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("Resume file size should not exceed 5MB");
        }

        // 3. Store Resume
        String storedFileName = fileStorageService.storeResume(resumeFile);

        // 4. Save Application
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
        jobApplication.setResumePreviewPath(null);

        JobApplication savedApplication =
                jobApplicationRepository.save(jobApplication);

        // 5. Email Notification (non-blocking)
        try {
            emailService.sendJobApplicationNotification(
                    job.getJobTitle(),
                    applicationRequest.getFullName(),
                    applicationRequest.getEmail(),
                    applicationRequest.getPhoneNumber(),
                    storedFileName,
                    resumeFile.getOriginalFilename()
            );
        } catch (Exception e) {
            System.err.println("Email failed: " + e.getMessage());
        }

        return savedApplication;
    }

    /**
     * ADMIN / DASHBOARD METHODS
     * (Restored to keep CareerController working)
     */

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

    public JobApplication updateApplicationStatus(Long id, String status, String notes) {
        Optional<JobApplication> optionalApplication =
                jobApplicationRepository.findById(id);

        if (optionalApplication.isPresent()) {
            JobApplication application = optionalApplication.get();
            application.setStatus(status);
            application.setNotes(notes);
            return jobApplicationRepository.save(application);
        }

        return null;
    }
}

