public JobApplication submitJobApplication(JobApplicationRequest applicationRequest) throws IOException {

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

    // ✅ PDF ONLY
    if (!"application/pdf".equalsIgnoreCase(resumeFile.getContentType())) {
        throw new RuntimeException("Only PDF resumes are allowed");
    }

    if (resumeFile.getSize() > 5 * 1024 * 1024) {
        throw new RuntimeException("Resume file size should not exceed 5MB");
    }

    // 3. Store Resume (ONE FILE ONLY)
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
    jobApplication.setResumePreviewPath(null); // 🔑 IMPORTANT

    JobApplication savedApplication =
            jobApplicationRepository.save(jobApplication);

    // 5. Email Notification (Optional)
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

