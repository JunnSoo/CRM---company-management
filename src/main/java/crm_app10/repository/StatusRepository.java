package crm_app10.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import entity.Status;

public class StatusRepository {
	public List<Status> findAll(){
		List<Status> listStatus = new ArrayList<Status>();
		String query = "SELECT *\r\n"
				+ "FROM status s";
		Connection connection =  MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Status status = new Status();
				status.setId(resultSet.getInt("id"));
				status.setName(resultSet.getString("name"));
				listStatus.add(status);
			}
			
		} catch (Exception e) {
			System.out.println("findAll user " +e.getMessage());
		}
		
		return listStatus;
	}
}
