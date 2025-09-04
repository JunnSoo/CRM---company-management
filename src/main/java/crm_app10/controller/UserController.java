package crm_app10.controller;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app10.services.UserServices;
import entity.Users;
@WebServlet(name="userController", urlPatterns = {"/user","/user-delete"})
public class UserController extends HttpServlet{
	
	private UserServices userServices = new UserServices();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		
		if(servletPath.equals("/user-delete")){
			String idUser = req.getParameter("id");
			userServices.deleteByID(Integer.parseInt(idUser));
		}
		List<Users> listUsers = userServices.getAllUser();
		System.out.println("Danh sach " + listUsers.size());
		req.setAttribute("listUser", listUsers);
		req.getRequestDispatcher("user_table.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
