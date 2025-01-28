package com.pal.portal.Jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pal.portal.Jobportal.entity.JobPostActivity;
import com.pal.portal.Jobportal.entity.JobSeekerApply;
import com.pal.portal.Jobportal.entity.JobSeekerProfile;



@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {
	
	 List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

	    List<JobSeekerApply> findByJob(JobPostActivity job);
}