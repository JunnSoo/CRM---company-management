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
}
