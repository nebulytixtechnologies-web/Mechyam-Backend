/*package com.mechyam.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;



@Entity
@Table(name = "JOBS")
public class Job {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_seq")
    @SequenceGenerator(name = "job_seq", sequenceName = "JOB_SEQ", allocationSize = 1)
    private Long id;
    
    @Column(name = "JOB_TITLE", nullable = false)
    private String jobTitle;
    
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "LOCATION")
    private String location;
    
    @Column(name = "JOB_TYPE") // FULL_TIME, PART_TIME, CONTRACT
    private String jobType;
    
    @Column(name = "EXPERIENCE_LEVEL") // ENTRY, MID, SENIOR
    private String experienceLevel;
    
    @Column(name = "DESCRIPTION", length = 4000)
    private String description;
    
    @Column(name = "REQUIREMENTS", length = 4000)
    private String requirements;
    
    @Column(name = "RESPONSIBILITIES", length = 4000)
    private String responsibilities;
    
    @Column(name = "SALARY_RANGE")
    private String salaryRange;
    
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    
    @Column(name = "POSTED_DATE")
    private LocalDate postedDate;
    
    @Column(name = "CLOSING_DATE")
    private LocalDate closingDate;
    
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobApplication> applications;
    
   
    // Constructors
    public Job() {
        this.postedDate = LocalDate.now();
        this.isActive = true;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }
    
    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }
    
    public String getResponsibilities() { return responsibilities; }
    public void setResponsibilities(String responsibilities) { this.responsibilities = responsibilities; }
    
    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDate getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDate postedDate) { this.postedDate = postedDate; }
    
    public LocalDate getClosingDate() { return closingDate; }
    public void setClosingDate(LocalDate closingDate) { this.closingDate = closingDate; }
    
    public List<JobApplication> getApplications() { return applications; }
    public void setApplications(List<JobApplication> applications) { this.applications = applications; }
} */

//package com.mechyam.entity;
//
//import jakarta.persistence.*;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Entity
//@Table(name = "JOBS")
//public class Job {
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_seq")
//    @SequenceGenerator(name = "job_seq", sequenceName = "JOB_SEQ", allocationSize = 1)
//    private Long id;
//    
//    @Column(name = "JOB_TITLE", nullable = false)
//    private String jobTitle;
//    
//    @Column(name = "DEPARTMENT")
//    private String department;
//    
//    @Column(name = "LOCATION")
//    private String location;
//    
//    @Column(name = "JOB_TYPE") // FULL_TIME, PART_TIME, CONTRACT
//    private String jobType;
//    
//    @Column(name = "EXPERIENCE_LEVEL") // ENTRY, MID, SENIOR
//    private String experienceLevel;
//    
//    @Column(name = "DESCRIPTION", length = 4000)
//    private String description;
//    
//    @Column(name = "REQUIREMENTS", length = 4000)
//    private String requirements;
//    
//    @Column(name = "RESPONSIBILITIES", length = 4000)
//    private String responsibilities;
//    
//    @Column(name = "SALARY_RANGE")
//    private String salaryRange;
//    
//    @Column(name = "IS_ACTIVE")
//    private Boolean isActive;
//    
//   
//    
//    @Column(name = "POSTED_DATE")
//    private LocalDate postedDate;
//    
//    @Column(name = "CLOSING_DATE")
//    private LocalDate closingDate;
//    
//    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore // ← THIS IS THE CRITICAL FIX!
//    private List<JobApplication> applications;
//    
//    // Constructors
//    public Job() {
//        this.postedDate = LocalDate.now();
//        this.isActive = true;
//    }
//    
//    // Getters and Setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    
//    public String getJobTitle() { return jobTitle; }
//    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
//    
//    public String getDepartment() { return department; }
//    public void setDepartment(String department) { this.department = department; }
//    
//    public String getLocation() { return location; }
//    public void setLocation(String location) { this.location = location; }
//    
//    public String getJobType() { return jobType; }
//    public void setJobType(String jobType) { this.jobType = jobType; }
//    
//    public String getExperienceLevel() { return experienceLevel; }
//    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }
//    
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//    
//    public String getRequirements() { return requirements; }
//    public void setRequirements(String requirements) { this.requirements = requirements; }
//    
//    public String getResponsibilities() { return responsibilities; }
//    public void setResponsibilities(String responsibilities) { this.responsibilities = responsibilities; }
//    
//    public String getSalaryRange() { return salaryRange; }
//    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }
//    
//    public Boolean getIsActive() { return isActive; }
//    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
//    
//    public LocalDate getPostedDate() { return postedDate; }
//    public void setPostedDate(LocalDate postedDate) { this.postedDate = postedDate; }
//    
//    public LocalDate getClosingDate() { return closingDate; }
//    public void setClosingDate(LocalDate closingDate) { this.closingDate = closingDate; }
//    
//    @JsonIgnore // ← ALSO ADD THIS TO THE GETTER!
//    public List<JobApplication> getApplications() { return applications; }
//    public void setApplications(List<JobApplication> applications) { this.applications = applications; }
//}


package com.mechyam.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "JOBS")
public class Job {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column(name = "JOB_TITLE", nullable = false)
    private String jobTitle;
    
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "LOCATION")
    private String location;
    
    @Column(name = "JOB_TYPE") // FULL_TIME, PART_TIME, CONTRACT
    private String jobType;
    
    @Column(name = "EXPERIENCE_LEVEL") // ENTRY, MID, SENIOR
    private String experienceLevel;
    
    @Column(name = "DESCRIPTION", length = 4000)
    private String description;
    
    @Column(name = "REQUIREMENTS", length = 4000)
    private String requirements;
    
    @Column(name = "RESPONSIBILITIES", length = 4000)
    private String responsibilities;
    
    @Column(name = "SALARY_RANGE")
    private String salaryRange;
    
    // ✅ ADD THIS FIELD - Number of job openings
    @Column(name = "NUMBER_OF_OPENINGS")
    private Integer numberOfOpenings;
    
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    
    @Column(name = "POSTED_DATE")
    private LocalDate postedDate;
    
    @Column(name = "CLOSING_DATE")
    private LocalDate closingDate;
    
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<JobApplication> applications;
    
    // Constructors
    public Job() {
        this.postedDate = LocalDate.now();
        this.isActive = true;
        this.numberOfOpenings = 1; // Default to 1 opening
    }
    
    // Parameterized constructor
    public Job(String jobTitle, String department, String location, String jobType, 
               String experienceLevel, String description, String requirements, 
               String responsibilities, String salaryRange, Integer numberOfOpenings, 
               LocalDate closingDate) {
        this();
        this.jobTitle = jobTitle;
        this.department = department;
        this.location = location;
        this.jobType = jobType;
        this.experienceLevel = experienceLevel;
        this.description = description;
        this.requirements = requirements;
        this.responsibilities = responsibilities;
        this.salaryRange = salaryRange;
        this.numberOfOpenings = numberOfOpenings;
        this.closingDate = closingDate;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }
    
    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }
    
    public String getResponsibilities() { return responsibilities; }
    public void setResponsibilities(String responsibilities) { this.responsibilities = responsibilities; }
    
    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }
    
    // ✅ ADD GETTER AND SETTER FOR NUMBER OF OPENINGS
    public Integer getNumberOfOpenings() { return numberOfOpenings; }
    public void setNumberOfOpenings(Integer numberOfOpenings) { 
        this.numberOfOpenings = numberOfOpenings; 
    }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDate getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDate postedDate) { this.postedDate = postedDate; }
    
    public LocalDate getClosingDate() { return closingDate; }
    public void setClosingDate(LocalDate closingDate) { this.closingDate = closingDate; }
    
    @JsonIgnore
    public List<JobApplication> getApplications() { return applications; }
    public void setApplications(List<JobApplication> applications) { this.applications = applications; }
}