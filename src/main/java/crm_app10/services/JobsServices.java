package crm_app10.services;
import java.util.List;

import crm_app10.repository.JobsRepository;
import entity.Jobs;
public class JobsServices {
	private JobsRepository jobsRepository = new JobsRepository();
	public List<Jobs> getAllJobs(){
		return jobsRepository.findAll();
	}
	
	public boolean insertJobs(String name, String start_date, String end_date) {
		return jobsRepository.save(name, start_date, end_date) > 0;
	}
	
	public Jobs getJobsById(int id) {
		return jobsRepository.findbyId(id);
	}
	
	public boolean updateJobs(int id, String name, String start_date, String end_date) {
		Jobs current  = jobsRepository.findbyId(id);
		if(current == null) {
			return false;
		}
		
		if (name == null || name.trim().isEmpty()) {
            name = current.getName();
        }
		
		if (start_date == null || start_date.trim().isEmpty()) {
            start_date = current.getStart_date();
        }
		
		if (end_date == null || end_date.trim().isEmpty()) {
            end_date = current.getEnd_date();
        }
		
		// Nếu không thay đổi gì thì coi như không update
        if (name.equals(current.getName()) && start_date.equals(current.getStart_date()) && end_date.equals(current.getEnd_date())) {
            return false;
        }
		return jobsRepository.updateById(id, name, start_date, end_date) >0;
	}
	
	public boolean deleteById(int id) {
		return jobsRepository.deleteById(id) >0;
	}
}
