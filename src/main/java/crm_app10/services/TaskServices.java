package crm_app10.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import crm_app10.repository.TaskRepository;
import crm_app10.repository.UserRepository;
import entity.Tasks;

public class TaskServices {
	private TaskRepository taskRepository = new TaskRepository();
	public List<Tasks> getAllTask(){
		return taskRepository.findAll();
	}
	
	public boolean insertTask(String name, String start_date, String end_date, int user_id, int job_id, int status_id) {
		return taskRepository.save(name, start_date, end_date, user_id, job_id, status_id) >0;
	}
	
	public Tasks getTaskById(int id) {
		return taskRepository.findbyId(id);
	}
	
	public List<Tasks> getTaskByUserId(int user_id){
		return taskRepository.findByUserIdV2(user_id);
	}
	
	public boolean updateTaskByID(int id, String name, String start_date, String end_date, int user_id, int job_id, int status_id) {
		Tasks current = taskRepository.findbyId(id);
		if(current == null) return false;
		
		if(name == null || name.trim().isEmpty()) {
			name = current.getName();
		}
		
		if(start_date == null || start_date.trim().isEmpty()) {
			start_date = current.getStart_date();
		}
		
		if(end_date == null || end_date.trim().isEmpty()) {
			end_date = current.getEnd_date();
		}
		
		return taskRepository.update(id, name, start_date, end_date, user_id, job_id, status_id)>0; 
	}
	
	public Map<String, List<Tasks>> getTaskByStatus(int user_id){
		List<Tasks> allTask = taskRepository.findByUserId(user_id);
		List<Tasks> todo = new ArrayList<Tasks>();
		List<Tasks>  inProgress = new ArrayList<Tasks>();
		List<Tasks> done = new ArrayList<Tasks>();
		
		for(Tasks t : allTask) {
			switch (t.getStatus_id()) {
			case 1: {
				todo.add(t);
				break;
			}
			case 2: {
				inProgress.add(t);
				break;
			}
			case 3: {
				done.add(t);
				break;
			}
			default:
				break;
			}
		}
		
		Map<String, List<Tasks>> map = new HashMap<String, List<Tasks>>();
		map.put("todo", todo);
		map.put("inProgress", inProgress);
		map.put("done",done);
		return map;
	}
	
	public Map<String, Integer> getTaskPercentages(int userId) throws SQLException {
        List<Tasks> tasks = taskRepository.findByUserId(userId);

        int total = tasks.size();
        int todo = 0, inProgress = 0, done = 0;

        for (Tasks t : tasks) {
            switch (t.getStatus_id()) {
                case 1: // chưa bắt đầu
                    todo++;
                    break;
                case 2: // đang làm
                    inProgress++;
                    break;
                case 3: // hoàn thành
                    done++;
                    break;
            }
        }
        
        Map<String, Integer> result = new HashMap<>();
        if (total > 0) {
            result.put("percentTodo", (todo * 100) / total);
            result.put("percentInProgress", (inProgress * 100) / total);
            result.put("percentDone", (done * 100) / total);
        } else {
            result.put("percentTodo", 0);
            result.put("percentInProgress", 0);
            result.put("percentDone", 0);
        }

        return result;
    }
	
	public Map<String, Map<String, List<Tasks>>> getGroupTaskByJobId(int job_id){
		// key1 = userName, key2 = statusName
		List<Tasks> listTask = taskRepository.findByJobId(job_id);
		
		Map<String, Map<String, List<Tasks>>> grouped = new LinkedHashMap<String, Map<String,List<Tasks>>>();
		
		for(Tasks t : listTask) {
			grouped
				.computeIfAbsent(t.getUser_id() + "|" + t.getUserName(), k -> new LinkedHashMap<>())
				.computeIfAbsent(t.getStatusName(), k -> new ArrayList<>())
				.add(t);
		}
		return grouped;
	}
	
	public Map<String, Integer> getTaskPercentagesByJobId(int job_id) throws SQLException {
        List<Tasks> tasks = taskRepository.findByJobId(job_id);

        int total = tasks.size();
        int todo = 0, inProgress = 0, done = 0;

        for (Tasks t : tasks) {
            switch (t.getStatus_id()) {
                case 1: // chưa bắt đầu
                    todo++;
                    break;
                case 2: // đang làm
                    inProgress++;
                    break;
                case 3: // hoàn thành
                    done++;
                    break;
            }
        }
        
        Map<String, Integer> result = new HashMap<>();
        if (total > 0) {
            result.put("percentTodo", (todo * 100) / total);
            result.put("percentInProgress", (inProgress * 100) / total);
            result.put("percentDone", (done * 100) / total);
        } else {
            result.put("percentTodo", 0);
            result.put("percentInProgress", 0);
            result.put("percentDone", 0);
        }

        return result;
    }
	
	public boolean deleteById(int id) {
		return taskRepository.deleteById(id)>0;
	}
}
