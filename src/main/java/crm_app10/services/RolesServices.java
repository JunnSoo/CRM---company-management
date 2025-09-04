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
}
