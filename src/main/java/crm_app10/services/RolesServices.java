package crm_app10.services;
import java.util.List;

import crm_app10.repository.RolesRepository;
import entity.Roles;
public class RolesServices {
	private RolesRepository rolesRepository = new RolesRepository();
	public List<Roles> getAllRole(){
		return rolesRepository.findAll();
	}
	
	public boolean insertRole(String name, String desc) {
		return rolesRepository.save(name, desc) >0;
	}
	
	public Roles getRoleById(int id) {
        return rolesRepository.findById(id);
    }
	
	public boolean updateRole(int id, String name, String desc) {
        Roles current = rolesRepository.findById(id);
        if (current == null) {
            return false; // không có id để update
        }

        if (name == null || name.trim().isEmpty()) {
            name = current.getName();
        }
        if (desc == null || desc.trim().isEmpty()) {
            desc = current.getDescription();
        }

        // Nếu không thay đổi gì thì coi như không update
        if (name.equals(current.getName()) && desc.equals(current.getDescription())) {
            return false;
        }

        return rolesRepository.updateById(id, name, desc) >0;
    }
	
	public boolean deleteByID(int id) {
		return rolesRepository.deleteByID(id)>0;
	}
}
