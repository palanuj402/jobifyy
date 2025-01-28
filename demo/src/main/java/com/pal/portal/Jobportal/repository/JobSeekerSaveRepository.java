package com.pal.portal.Jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pal.portal.Jobportal.entity.JobPostActivity;
import com.pal.portal.Jobportal.entity.JobSeekerProfile;
import com.pal.portal.Jobportal.entity.JobSeekerSave;



@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer>{
	
	public List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);
    
}