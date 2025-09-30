package crm_app10.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import entity.Tasks;

public class TaskRepository {
	public List<Tasks> findAll(){
		List<Tasks> listTasks = new ArrayList<Tasks>();
		String query ="SELECT t.id, t.name , j.name AS job_name , u.fullname ,t.start_date ,t.end_date ,s.name AS status_name \r\n"
				+ "FROM tasks t \r\n"
				+ "JOIN jobs j ON t.job_id  = j.id \r\n"
				+ "JOIN users u  ON t.user_id  = u.id \r\n"
				+ "JOIN status s ON t.status_id = s.id ";
		
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Tasks task = new Tasks();
				task.setId(rs.getInt("id"));
				task.setName(rs.getString("name"));
				task.setJobName(rs.getString("job_name"));
				task.setUserName(rs.getString("fullname"));
				task.setStart_date(rs.getString("start_date"));
				task.setEnd_date(rs.getString("end_date"));
				task.setStatusName(rs.getString("status_name"));
				listTasks.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi cau truy van task : "+ e.getMessage());
		}
		
		return listTasks;
	}
	
	public int save(String name,String start_date, String end_date, int user_id, int job_id, int status_id) {
		int rowCount  = 0;
		String query = "INSERT INTO tasks (name,start_date,end_date, user_id ,job_id ,status_id ) VALUES (?,?,?,?,?,?)";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, start_date);
			statement.setString(3,end_date);
			statement.setInt(4, user_id);
			statement.setInt(5, job_id);
			statement.setInt(6, status_id);
			
			rowCount = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi them task "+e.getMessage());
		}
		
		return rowCount;
	}
	
	public Tasks findbyId(int id) {
		Tasks task = null;
		String query = "SELECT *\r\n"
				+ "FROM tasks t\r\n"
				+ "WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				task = new Tasks();
				task.setId(rs.getInt("id"));
				task.setName(rs.getString("name"));
				task.setStart_date(rs.getString("start_date"));
				task.setEnd_date(rs.getString("end_date"));
				task.setUser_id(rs.getInt("user_id"));
				task.setJob_id(rs.getInt("job_id"));
				task.setStatus_id(rs.getInt("status_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("loi tim task theo id: " + e.getMessage());
		}
		return task;
	}
	
	public List<Tasks> findByUserId(int id){
		List<Tasks> listTask = new ArrayList<Tasks>();
		String query = "SELECT *\r\n"
				+ "FROM tasks t \r\n"
				+ "WHERE t.user_id = ?";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Tasks task = new Tasks();
				task.setId(rs.getInt("id"));
				task.setName(rs.getString("name"));
				task.setStart_date(rs.getString("start_date"));
				task.setEnd_date(rs.getString("end_date"));
				task.setUser_id(rs.getInt("user_id"));
				task.setJob_id(rs.getInt("job_id"));
				task.setStatus_id(rs.getInt("status_id"));
				listTask.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi findByUserId: " +e.getMessage());
		}
		
		return listTask;
	}
	
	
	public List<Tasks> findByJobId(int jobId) {
		List<Tasks> listTask = new ArrayList<Tasks>();
        String sql = "SELECT t.id, t.name, t.start_date, t.end_date,\r\n"
        		+ "                   t.user_id, t.job_id, t.status_id,\r\n"
        		+ "                   u.fullname AS userName,\r\n"
        		+ "                   j.name     AS jobName,\r\n"
        		+ "                   s.name     AS statusName\r\n"
        		+ "FROM tasks t\r\n"
        		+ "JOIN users  u ON u.id = t.user_id\r\n"
        		+ "JOIN jobs   j ON j.id = t.job_id\r\n"
        		+ "JOIN status s ON s.id = t.status_id\r\n"
        		+ "WHERE t.job_id = ?\r\n"
        		+ "ORDER BY u.fullname, s.id, t.start_date";
        
        Connection connection = MySQLConfig.getConnection();
        try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, jobId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Tasks task = new Tasks();
				task.setId(rs.getInt("id"));
				task.setName(rs.getString("name"));
				task.setStart_date(rs.getString("start_date"));
				task.setEnd_date(rs.getString("end_date"));
				task.setUser_id(rs.getInt("user_id"));
				task.setJob_id(rs.getInt("job_id"));
				task.setStatus_id(rs.getInt("status_id"));
				task.setUserName(rs.getString("userName"));
				task.setJobName(rs.getString("jobName"));
				task.setStatusName(rs.getString("statusName"));
				listTask.add(task);
			}
		} catch (Exception e) {
			System.out.println("Loi find job by id: " +e.getMessage());
			e.printStackTrace();
		}
        return listTask;
    }    
	
	public List<Tasks> findByUserIdV2(int user_id){
		List<Tasks> listTask = new ArrayList<Tasks>();
        String sql = "SELECT t.id, t.name, t.start_date, t.end_date,\r\n"
        		+ "                   t.user_id, t.job_id, t.status_id,\r\n"
        		+ "                   u.fullname AS userName,\r\n"
        		+ "                   j.name     AS jobName,\r\n"
        		+ "                   s.name     AS statusName\r\n"
        		+ "FROM tasks t\r\n"
        		+ "JOIN users  u ON u.id = t.user_id\r\n"
        		+ "JOIN jobs   j ON j.id = t.job_id\r\n"
        		+ "JOIN status s ON s.id = t.status_id\r\n"
        		+ "WHERE t.user_id = ?\r\n"
        		+ "ORDER BY t.start_date";
        
        Connection connection = MySQLConfig.getConnection();
        try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Tasks task = new Tasks();
				task.setId(rs.getInt("id"));
				task.setName(rs.getString("name"));
				task.setStart_date(rs.getString("start_date"));
				task.setEnd_date(rs.getString("end_date"));
				task.setUser_id(rs.getInt("user_id"));
				task.setJob_id(rs.getInt("job_id"));
				task.setStatus_id(rs.getInt("status_id"));
				task.setUserName(rs.getString("userName"));
				task.setJobName(rs.getString("jobName"));
				task.setStatusName(rs.getString("statusName"));
				listTask.add(task);
			}
		} catch (Exception e) {
			System.out.println("Loi find job by user_id: " +e.getMessage());
			e.printStackTrace();
		}
        return listTask;
	}
	
	public int update(int id, String name, String start_date, String end_date, int user_id, int job_id, int status_id) {
		int rowAffected = 0;
		String query = "UPDATE tasks SET name=?, start_date = ?, end_date = ?, user_id = ?, job_id = ?, status_id = ? WHERE id =?";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, start_date);
			statement.setString(3, end_date);
			statement.setInt(4, user_id);
			statement.setInt(5, job_id);
			statement.setInt(6, status_id);
			statement.setInt(7, id);
			
			rowAffected = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Loi update task " + e.getMessage());
			e.printStackTrace();
		}
		return rowAffected;
	}
	
	public int deleteById(int id) {
		int rowCount =0;
		String query = "DELETE FROM tasks t WHERE t.id = ?";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			rowCount = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi xoa task: "+ e.getMessage());
		}
		return rowCount;
	}
}
