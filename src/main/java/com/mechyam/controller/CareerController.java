//package com.mechyam.controller;
//
//import com.mechyam.dto.ApiResponse;
//import com.mechyam.dto.JobApplicationRequest;
//import com.mechyam.entity.Job;
//import com.mechyam.entity.JobApplication;
//import com.mechyam.service.CareerService;
//import com.mechyam.service.FileStorageService;
//import com.mechyam.service.JobService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.nio.file.Path;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/career")
//@CrossOrigin(origins = "*")
//public class CareerController {
//    
//    @Autowired
//    private JobService jobService;
//    
//    @Autowired
//    private CareerService careerService;
//    
//    
//    @Autowired
//    private FileStorageService fileStorageService;
//    
//    
//    @DeleteMapping("/jobs/{id}")
//    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long id) {
//        try {
//            boolean isDeleted = jobService.deleteJob(id);
//            if (isDeleted) {
//                return ResponseEntity.ok(ApiResponse.success(
//                    "Job deleted successfully", 
//                    null
//                ));
//            } else {
//                return ResponseEntity.badRequest()
//                        .body(ApiResponse.error("Job not found with id: " + id));
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error("Failed to delete job: " + e.getMessage()));
//        }
//    }
//    
//    @PostMapping("/jobs")
//    public ResponseEntity<ApiResponse> createJob(@RequestBody Job job) {
//        try {
//            Job savedJob = jobService.createJob(job);
//            return ResponseEntity.ok(ApiResponse.success(
//                "Job created successfully", 
//                savedJob
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error("Failed to create job: " + e.getMessage()));
//        }
//    }
//   
// 
//    // Job endpoints
//    @GetMapping("/jobs")
//    public ResponseEntity<ApiResponse> getAllActiveJobs() {
//        try {
//            List<Job> jobs = jobService.getAllActiveJobs();
//            return ResponseEntity.ok(ApiResponse.success(
//                "Active jobs retrieved successfully", 
//                jobs
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error("Failed to retrieve jobs: " + e.getMessage()));
//        }
//    }
//    
//    @GetMapping("/jobs/all")
//    public ResponseEntity<ApiResponse> getAllJobs() {
//        try {
//            List<Job> jobs = jobService.getAllJobs();
//            return ResponseEntity.ok(ApiResponse.success(
//                "All jobs retrieved successfully", 
//                jobs
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error("Failed to retrieve jobs: " + e.getMessage()));
//        }
//    }
//    
//    @GetMapping("/jobs/{id}")
//    public ResponseEntity<ApiResponse> getJobById(@PathVariable Long id) {
//        try {
//            Optional<Job> job = jobService.getJobById(id);
//            if (job.isPresent()) {
//                return ResponseEntity.ok(ApiResponse.success(
//                    "Job retrieved successfully", 
//                    job.get()
//                ));
//            } else {
//                return ResponseEntity.badRequest()
//                        .body(ApiResponse.error("Job not found with id: " + id));
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error("Failed to retrieve job: " + e.getMessage()));
//        }
//    }
//    
//    @GetMapping("/departments")
//    public ResponseEntity<ApiResponse> getAllDepartments() {
//        try {
//            List<String> departments = jobService.getAllDepartments();
//            return ResponseEntity.ok(ApiResponse.success(
//                "Departments retrieved successfully", 
//                departments
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error("Failed to retrieve departments: " + e.getMessage()));
//        }
//    }
//    
//    // Job Application endpoints
//    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ApiResponse> submitJobApplication(
//            @ModelAttribute JobApplicationRequest applicationRequest) {
//        try {
//            JobApplication application = careerService.submitJobApplication(applicationRequest);
//            return ResponseEntity.ok(ApiResponse.success(
//                "Job application submitted successfully", 
//                application.getId()
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error("Failed to submit application: " + e.getMessage()));
//        }
//    }
//    
//    @GetMapping("/applications")
//    public ResponseEntity<ApiResponse> getAllApplications() {
//        try {
//            List<JobApplication> applications = careerService.getAllApplications();
//            return ResponseEntity.ok(ApiResponse.success(
//                "Applications retrieved successfully", 
//                applications
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error("Failed to retrieve applications: " + e.getMessage()));
//        }
//    }
//    
//    @GetMapping("/applications/job/{jobId}")
//    public ResponseEntity<ApiResponse> getApplicationsByJobId(@PathVariable Long jobId) {
//        try {
//            List<JobApplication> applications = careerService.getApplicationsByJobId(jobId);
//            return ResponseEntity.ok(ApiResponse.success(
//                "Applications retrieved successfully", 
//                applications
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error("Failed to retrieve applications: " + e.getMessage()));
//        }
//    }
//    
//    @GetMapping("/applications/status/{status}")
//    public ResponseEntity<ApiResponse> getApplicationsByStatus(@PathVariable String status) {
//        try {
//            List<JobApplication> applications = careerService.getApplicationsByStatus(status);
//            return ResponseEntity.ok(ApiResponse.success(
//                "Applications retrieved successfully", 
//                applications
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error("Failed to retrieve applications: " + e.getMessage()));
//        }
//    }
//    
//    @PutMapping("/applications/{id}/status")
//    public ResponseEntity<ApiResponse> updateApplicationStatus(
//            @PathVariable Long id,
//            @RequestParam String status,
//            @RequestParam(required = false) String notes) {
//        try {
//            JobApplication application = careerService.updateApplicationStatus(id, status, notes);
//            if (application != null) {
//                return ResponseEntity.ok(ApiResponse.success(
//                    "Application status updated successfully", 
//                    application
//                ));
//            } else {
//                return ResponseEntity.badRequest()
//                        .body(ApiResponse.error("Application not found with id: " + id));
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error("Failed to update application status: " + e.getMessage()));
//        }
//    }
//    
//    // Resume download endpoint - CORRECTED VERSION
//    @GetMapping("/applications/{id}/resume")
//    public ResponseEntity<Resource> downloadResume(@PathVariable Long id) {
//        try {
//            Optional<JobApplication> application = careerService.getApplicationById(id);
//            if (application.isPresent()) {
//                JobApplication jobApplication = application.get();
//                Path filePath = fileStorageService.loadFile(jobApplication.getResumeFilePath());
//                Resource resource = new UrlResource(filePath.toUri());
//                
//                if (resource.exists() && resource.isReadable()) {
//                    return ResponseEntity.ok()
//                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                            .header(HttpHeaders.CONTENT_DISPOSITION, 
//                                    "attachment; filename=\"" + jobApplication.getResumeFileName() + "\"")
//                            .body(resource);
//                }
//            }
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//}


package com.mechyam.controller;

import com.mechyam.dto.ApiResponse;
import com.mechyam.dto.JobApplicationRequest;
import com.mechyam.entity.Job;
import com.mechyam.entity.JobApplication;
import com.mechyam.service.CareerService;
import com.mechyam.service.FileStorageService;
import com.mechyam.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/career")
@CrossOrigin(origins = "*")
public class CareerController {
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private CareerService careerService;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    // ✅ ADD THIS UPDATE ENDPOINT
    @PutMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse> updateJob(@PathVariable Long id, @RequestBody Job jobDetails) {
        try {
            Job updatedJob = jobService.updateJob(id, jobDetails);
            if (updatedJob != null) {
                return ResponseEntity.ok(ApiResponse.success(
                    "Job updated successfully", 
                    updatedJob
                ));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Job not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update job: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long id) {
        try {
            boolean isDeleted = jobService.deleteJob(id);
            if (isDeleted) {
                return ResponseEntity.ok(ApiResponse.success(
                    "Job deleted successfully", 
                    null
                ));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Job not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to delete job: " + e.getMessage()));
        }
    }
    
    @PostMapping("/jobs")
    public ResponseEntity<ApiResponse> createJob(@RequestBody Job job) {
        try {
            Job savedJob = jobService.createJob(job);
            return ResponseEntity.ok(ApiResponse.success(
                "Job created successfully", 
                savedJob
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to create job: " + e.getMessage()));
        }
    }
   
    // Job endpoints
    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse> getAllActiveJobs() {
        try {
            List<Job> jobs = jobService.getAllActiveJobs();
            return ResponseEntity.ok(ApiResponse.success(
                "Active jobs retrieved successfully", 
                jobs
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve jobs: " + e.getMessage()));
        }
    }
    
    @GetMapping("/jobs/all")
    public ResponseEntity<ApiResponse> getAllJobs() {
        try {
            List<Job> jobs = jobService.getAllJobs();
            return ResponseEntity.ok(ApiResponse.success(
                "All jobs retrieved successfully", 
                jobs
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve jobs: " + e.getMessage()));
        }
    }
    
    @GetMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse> getJobById(@PathVariable Long id) {
        try {
            Optional<Job> job = jobService.getJobById(id);
            if (job.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(
                    "Job retrieved successfully", 
                    job.get()
                ));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Job not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve job: " + e.getMessage()));
        }
    }
    
    @GetMapping("/departments")
    public ResponseEntity<ApiResponse> getAllDepartments() {
        try {
            List<String> departments = jobService.getAllDepartments();
            return ResponseEntity.ok(ApiResponse.success(
                "Departments retrieved successfully", 
                departments
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve departments: " + e.getMessage()));
        }
    }
    
    // Job Application endpoints
    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> submitJobApplication(
            @ModelAttribute JobApplicationRequest applicationRequest) {
        try {
            JobApplication application = careerService.submitJobApplication(applicationRequest);
            return ResponseEntity.ok(ApiResponse.success(
                "Job application submitted successfully", 
                application.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to submit application: " + e.getMessage()));
        }
    }
    
    @GetMapping("/applications")
    public ResponseEntity<ApiResponse> getAllApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            var applicationsPage = careerService.getAllApplications(page, size);
            return ResponseEntity.ok(ApiResponse.success(
                "Applications retrieved successfully",
                applicationsPage
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve applications: " + e.getMessage()));
        }
    }

    
    @GetMapping("/applications/job/{jobId}")
    public ResponseEntity<ApiResponse> getApplicationsByJobId(@PathVariable Long jobId) {
        try {
            List<JobApplication> applications = careerService.getApplicationsByJobId(jobId);
            return ResponseEntity.ok(ApiResponse.success(
                "Applications retrieved successfully", 
                applications
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve applications: " + e.getMessage()));
        }
    }
    
    @GetMapping("/applications/status/{status}")
    public ResponseEntity<ApiResponse> getApplicationsByStatus(@PathVariable String status) {
        try {
            List<JobApplication> applications = careerService.getApplicationsByStatus(status);
            return ResponseEntity.ok(ApiResponse.success(
                "Applications retrieved successfully", 
                applications
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve applications: " + e.getMessage()));
        }
    }
    
    @PutMapping("/applications/{id}/status")
    public ResponseEntity<ApiResponse> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String notes) {
        try {
            JobApplication application = careerService.updateApplicationStatus(id, status, notes);
            if (application != null) {
                return ResponseEntity.ok(ApiResponse.success(
                    "Application status updated successfully", 
                    application
                ));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Application not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update application status: " + e.getMessage()));
        }
    }
    
    // Resume download endpoint - CORRECTED VERSION
    @GetMapping("/applications/{id}/resume")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long id) {
        try {
            Optional<JobApplication> application = careerService.getApplicationById(id);
            if (application.isPresent()) {
                JobApplication jobApplication = application.get();
                Path filePath = fileStorageService.loadFile(jobApplication.getResumeFilePath());
                Resource resource = new UrlResource(filePath.toUri());
                
                if (resource.exists() && resource.isReadable()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .header(HttpHeaders.CONTENT_DISPOSITION, 
                                    "attachment; filename=\"" + jobApplication.getResumeFileName() + "\"")
                            .body(resource);
                }
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

