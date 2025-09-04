package crm_app10.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import config.MySQLConfig;

import entity.Users;
public class UserRepository {
	/* 
	 * Cach dat ten ham trong repo de goi nho toi cau truy van
	 * 
	 * VD: SELECT *     :find
	 * 	   FROM users u
	 * 	   WHERE u.email = '' AND u.password = '' : by
	 *		=> findByEmailAndPassword
	 *
	 *Join 2 bang thi khong co nguyen tac
	 * */
	
	public List<Users> findAll(){
		List<Users> listUsers = new ArrayList<Users>();
		String query = "SELECT * FROM users u JOIN roles r  ON u.role_id = r.id \r\n";
		Connection connection =  MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Users users = new Users();
				users.setName(resultSet.getString("fullname"));
				users.setEmail(resultSet.getString("email"));
				users.setId(resultSet.getInt("id"));
				users.setRoleDescription(resultSet.getString("description"));
				listUsers.add(users);
			}
			
		} catch (Exception e) {
			System.out.println("findAll user " +e.getMessage());
		}
		
		
		return listUsers;
	}
	
	public int deleteById(int id) {
		
		int rowCount = 0;
		String query = "DELETE FROM users u WHERE u.id = ?";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			rowCount= statement.executeUpdate();
		}catch (Exception e) {
			System.out.println("Error: "+ e.getMessage());
		}
		
		return rowCount;
	}
}
