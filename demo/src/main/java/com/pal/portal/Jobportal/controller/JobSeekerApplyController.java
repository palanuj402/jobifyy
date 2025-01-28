package com.pal.portal.Jobportal.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pal.portal.Jobportal.entity.JobPostActivity;
import com.pal.portal.Jobportal.entity.JobSeekerApply;
import com.pal.portal.Jobportal.entity.JobSeekerProfile;
import com.pal.portal.Jobportal.entity.JobSeekerSave;
import com.pal.portal.Jobportal.entity.RecruiterProfile;
import com.pal.portal.Jobportal.entity.Users;
import com.pal.portal.Jobportal.service.JobPostActivityService;
import com.pal.portal.Jobportal.service.JobProfileSeekerService;
import com.pal.portal.Jobportal.service.JobSeekerApplyService;
import com.pal.portal.Jobportal.service.JobSeekerSaveService;
import com.pal.portal.Jobportal.service.RecruiterProfileService;
import com.pal.portal.Jobportal.service.UserService;

@Controller
public class JobSeekerApplyController {

	private final JobPostActivityService jobPostActivityservice;
	private final UserService userservice;
	private final JobSeekerApplyService jobSeekerApplyservice;
	private final JobSeekerSaveService jobSeekerSaveservice;
	private final RecruiterProfileService recruiterProfileservice;
	private final JobProfileSeekerService jobProfileSeekerservice;
	
	@Autowired
	public JobSeekerApplyController(JobPostActivityService jobPostActivityservice, UserService userservice,
			JobSeekerApplyService jobSeekerApplyservice,JobSeekerSaveService jobSeekerSaveservice,
			RecruiterProfileService recruiterProfileservice,JobProfileSeekerService jobProfileSeekerservice) {
		super();
		this.jobPostActivityservice = jobPostActivityservice;
		this.userservice = userservice;
		this.jobSeekerApplyservice = jobSeekerApplyservice;
		this.jobSeekerSaveservice = jobSeekerSaveservice;
		this.recruiterProfileservice = recruiterProfileservice;
		this.jobProfileSeekerservice = jobProfileSeekerservice;
	}
	
	@GetMapping("job-details-apply/{id}")
	public String display(@PathVariable("id") int id, Model model) {
		JobPostActivity jobPostActivity = jobPostActivityservice.getOne(id);
		
		List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyservice.getJobCandidates(jobPostActivity);
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveservice.getJobCandidates(jobPostActivity);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile user = recruiterProfileservice.getCurrentRecruiterProfile();
                if (user != null) {
                    model.addAttribute("applyList", jobSeekerApplyList);
                }
            } else {
                JobSeekerProfile user = jobProfileSeekerservice.getCurrentSeekerProfile();
                if (user != null) {
                    boolean exists = false;
                    boolean saved = false;
                    for (JobSeekerApply jobSeekerApply : jobSeekerApplyList) {
                        if (jobSeekerApply.getUserId().getUserAccountId() == user.getUserAccountId()) {
                            exists = true;
                            break;
                        }
                    }
                    for (JobSeekerSave jobSeekerSave : jobSeekerSaveList) {
                        if (jobSeekerSave.getUserId().getUserAccountId() == user.getUserAccountId()) {
                            saved = true;
                            break;
                        }
                    }
                    model.addAttribute("alreadyApplied", exists);
                    model.addAttribute("alreadySaved", saved);
                }
            }
        }

        JobSeekerApply jobSeekerApply = new JobSeekerApply();
        model.addAttribute("applyJob", jobSeekerApply);
        
		model.addAttribute("jobDetails", jobPostActivity);
		model.addAttribute("user", userservice.getCurrentUserProfile());
		
		return "job-details";
	}
	
	@PostMapping("job-details/apply/{id}")
    public String apply(@PathVariable("id") int id, JobSeekerApply jobSeekerApply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = userservice.findByEmail(currentUsername);
            Optional<JobSeekerProfile> seekerProfile = jobProfileSeekerservice.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityservice.getOne(id);
            if (seekerProfile.isPresent() && jobPostActivity != null) {
                jobSeekerApply = new JobSeekerApply();
                jobSeekerApply.setUserId(seekerProfile.get());
                jobSeekerApply.setJob(jobPostActivity);
                jobSeekerApply.setApplyDate(new Date());
            } else {
                throw new RuntimeException("User not found");
            }
            jobSeekerApplyservice.addNew(jobSeekerApply);
        }

        return "redirect:/dashboard/";
    }
}