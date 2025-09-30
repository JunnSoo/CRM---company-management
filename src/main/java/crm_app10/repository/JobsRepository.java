package crm_app10.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import config.MySQLConfig;
import entity.Jobs;
public class JobsRepository {
	public List<Jobs> findAll() {
		List<Jobs> listJobs = new ArrayList<Jobs>();
		String query = "SELECT * \r\n"
				+ "FROM jobs j";
		
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Jobs jobs = new Jobs();
				jobs.setId(resultSet.getInt("id"));
				jobs.setName(resultSet.getString("name"));
				jobs.setStart_date(resultSet.getString("start_date"));
				jobs.setEnd_date(resultSet.getString("end_date"));
				listJobs.add(jobs);
			}
		} catch (Exception e) {
			System.out.println("findAll(): " + e.getMessage());
		}	
		
		return listJobs;
	}
	
	public int save(String name, String start_date, String end_date){
		int rowAffected =0;
		String query = "INSERT INTO jobs (name,start_date ,end_date ) VALUES (?,?,?)";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, start_date);
			statement.setString(3, end_date);
			
			rowAffected = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Loi them jobs: " + e.getMessage());
			e.printStackTrace();
		}
		
		return rowAffected;
	}
	
	public Jobs findbyId(int id) {
		Jobs job = null;
		String query = "SELECT * FROM jobs j \r\n"
				+ "WHERE j.id = ?";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				job = new Jobs();
				job.setId(rs.getInt("id"));
				job.setName(rs.getString("name"));
				job.setStart_date(rs.getString("start_date"));
				job.setEnd_date(rs.getString("end_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi : " +e.getMessage());
		}
		return job;
	}
	
	public int updateById(int id, String name, String start_date, String end_date) {
		int row_affected =0;
		String query = "UPDATE jobs SET name= ?, start_date= ?, end_date = ? WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, start_date);
			statement.setString(3, end_date);
			statement.setInt(4, id);
			row_affected = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Loi : "+ e.getMessage());
			e.printStackTrace();
		}
		
		return row_affected;
	}
	
	public int deleteById(int id) {
		int rowCount =0;
		String query = "DELETE FROM jobs j WHERE j.id = ?";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			rowCount = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi: "+ e.getMessage());
		}
		return rowCount;
	}
}
