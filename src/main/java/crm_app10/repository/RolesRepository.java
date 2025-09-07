package crm_app10.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.management.relation.Role;

import config.MySQLConfig;
import entity.Roles;
public class RolesRepository {
	public List<Roles> findAll(){
		List<Roles> listRoles = new ArrayList<Roles>();
		String query ="SELECT  *\r\n"
				+ "FROM roles r";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Roles roles = new Roles();
				roles.setId(resultSet.getInt("id"));
				roles.setName(resultSet.getString("name"));
				roles.setDescription(resultSet.getString("description"));
				listRoles.add(roles);
			}
		} catch (Exception e) {
			System.out.println("findAll roles "+ e.getMessage());
		}
		
		return listRoles; 
	}
	
	public int save(String name, String desc) {  // ham them du lieu
		int rowCount = 0;
		String query = "INSERT INTO roles (name,description ) VALUES (?,?);\r\n";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, desc);
			
			rowCount= statement.executeUpdate();
		}catch (Exception e) {
			System.out.println("Error: "+ e.getMessage());
		}
		
		return rowCount;
	}
	
	public Roles findById(int id) {
		Roles role = null;
        try (Connection conn = MySQLConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT id, name, description FROM roles WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	role = new Roles();
                    role.setId(rs.getInt("id"));
                    role.setName(rs.getString("name"));
                    role.setDescription(rs.getString("description"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
	
	public int updateById(int id, String name, String description) {
        int rowsAffected = 0;
        try (Connection conn = MySQLConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "UPDATE roles SET name = ?, description = ? WHERE id = ?")) {
            
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, id);

            rowsAffected = ps.executeUpdate(); // trả về số dòng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            rowsAffected = -1; // quy ước -1 là lỗi
        }
        return rowsAffected;
    }
	
	public int deleteByID(int id) {
		int rowCount =0;
		String query = "DELETE FROM roles r WHERE r.id = ?";
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, id);
			
			rowCount = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: "+ e.getMessage());
		}
		return rowCount;
	}
}
