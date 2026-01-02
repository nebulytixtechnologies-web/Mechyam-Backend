package com.mechyam.repository;

import com.mechyam.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByIsActiveTrueOrderByPostedDateDesc();
    List<Job> findByDepartmentAndIsActiveTrue(String department);
    List<Job> findByJobTypeAndIsActiveTrue(String jobType);
    
    @Query("SELECT DISTINCT j.department FROM Job j WHERE j.isActive = true")
    List<String> findDistinctDepartments();
}