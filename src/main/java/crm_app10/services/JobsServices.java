package crm_app10.services;
import java.util.List;

import crm_app10.repository.JobsRepository;
import entity.Jobs;
public class JobsServices {
	private JobsRepository jobsRepository = new JobsRepository();
	public List<Jobs> getAllJobs(){
		return jobsRepository.findAll();
		
	}
}
