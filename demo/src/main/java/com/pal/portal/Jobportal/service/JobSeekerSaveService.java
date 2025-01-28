package com.pal.portal.Jobportal.service;

import org.springframework.stereotype.Service;

import com.pal.portal.Jobportal.entity.JobPostActivity;
import com.pal.portal.Jobportal.entity.JobSeekerProfile;
import com.pal.portal.Jobportal.entity.JobSeekerSave;
import com.pal.portal.Jobportal.repository.JobSeekerSaveRepository;

import java.util.List;

@Service
public class JobSeekerSaveService {
	
	private final JobSeekerSaveRepository jobSeekerSaveRepository;

    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId) {
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
        return jobSeekerSaveRepository.findByJob(job);
    }

	public void addNew(JobSeekerSave jobSeekerSave) {
		jobSeekerSaveRepository.save(jobSeekerSave);
		
	}
    
}