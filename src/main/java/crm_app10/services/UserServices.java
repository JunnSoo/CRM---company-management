package crm_app10.services;
import java.util.List;

import crm_app10.repository.UserRepository;
import entity.Users;
public class UserServices {
	private UserRepository userRepository = new UserRepository();
	public List<Users> getAllUser(){
		return userRepository.findAll();
		
	}
	
	public boolean insertUser(String email, String password, String fullname, String country, int role_id) {
		return userRepository.save(email, password, fullname, country, role_id) >0;
	}
	
	public Users getUserById(int id) {
		return userRepository.findbyId(id);
	}
	
	public boolean updateUserById(int id, String email, String fullname, String country) {
		Users current = userRepository.findbyId(id);
		if (current == null) {
            return false; // không có id để update
        }

        if (fullname == null || fullname.trim().isEmpty()) {
            fullname = current.getName();
        }
        
        if (email == null || email.trim().isEmpty()) {
            email = current.getEmail();
        }
        
        if (country == null || country.trim().isEmpty()) {
            country = current.getCountry();
        }
        
        // Nếu không thay đổi gì thì coi như không update
        if (email.equals(current.getEmail())&& fullname.equals(current.getName()) && country.equals(current.getCountry())) {
            return false;
        }

        return userRepository.update(id,email,fullname,country) >0;
	}
	
	public boolean deleteByID(int id) {
		return userRepository.deleteById(id) > 0;
	}
}
