package crm_app10.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.CallableStatement;
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
				users.setCountry(resultSet.getString("country"));
				users.setRoleDescription(resultSet.getString("description"));
				listUsers.add(users);
			}
			
		} catch (Exception e) {
			System.out.println("findAll user " +e.getMessage());
		}
		
		
		return listUsers;
	}
	
	public  int save(String email, String password, String fullname, String country, int role_id) {
		int rowCount = 0;
		String query = "INSERT INTO users (email,password,fullname,role_id,country) VALUES (?,?,?,?,?)";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statment = connection.prepareStatement(query);
			statment.setString(1, email);
			statment.setString(2, password);
			statment.setString(3, fullname);
			statment.setInt(4, role_id);
			statment.setString(5, country);
			
			rowCount = statment.executeUpdate();
		} catch (Exception e) {
			System.out.println("Loi them moi user: " + e.getMessage());
			e.printStackTrace();
		}
			return rowCount;
	}
	
	public Users findbyId(int id){
		Users user =null;
		String query = "SELECT u.id, u.email ,u.country ,u.fullname,r.description \r\n"
				+ "FROM users u \r\n"
				+ "JOIN roles r ON u.role_id  = r.id \r\n"
				+ "Where u.id = ?";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				user = new Users();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setCountry(rs.getString("country"));
				user.setName(rs.getString("fullname"));
				user.setRoleDescription(rs.getString("description"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi khong tim thay user: " +e.getMessage());
		}		
		return user;
	}
	
	public int update(int id, String email, String fullname, String country) {
		int rowAffected = 0;
		String query = "UPDATE users SET email=?, fullname=?, country =? WHERE id =?";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, fullname);
			statement.setString(3, country);
			statement.setInt(4, id);
			
			rowAffected = statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Loi update user " + e.getMessage());
			e.printStackTrace();
		}
		return rowAffected;
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
