package crm_app10.controller;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app10.services.RolesServices;
import crm_app10.services.TaskServices;
import crm_app10.services.UserServices;
import entity.Roles;
import entity.Tasks;
import entity.Users;
@WebServlet(name="userController", urlPatterns = {"/user","/user-add","/user-edit","/user-delete","/user-detail"})
public class UserController extends HttpServlet{
	
	private UserServices userServices = new UserServices();
	private RolesServices rolesService = new RolesServices();
	private TaskServices taskServices = new TaskServices();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();
		try {
			switch (path) {
			case "/user": {
				List<Users> listUsers = userServices.getAllUser();
				System.out.println("Danh sach " + listUsers.size());
				req.setAttribute("listUser", listUsers);
				req.getRequestDispatcher("user_table.jsp").forward(req, resp);
				return;
			}
			case "/user-add":{
				req.setAttribute("roles", rolesService.getAllRole());
				req.getRequestDispatcher("user-add.jsp").forward(req, resp);
				return;
			}
			case "/user-edit":{
				String idStr = req.getParameter("id");
                if (idStr == null || idStr.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/user?msg=missing_id");
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/user?msg=bad_id");
                    return;
                }
                Users user = userServices.getUserById(id);
                if (user == null) {
                    resp.sendRedirect(req.getContextPath() + "/user?msg=not_found");
                    return;
                }
                req.setAttribute("user", user);
                req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
                return;
			}
			case "/user-detail":{
				String idStr = req.getParameter("id");
                if (idStr == null || idStr.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/user?msg=missing_id");
                    return;
                }
                int user_id;
                try {
                    user_id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/user?msg=bad_id");
                    return;
                }
                
                Users user = userServices.getUserById(user_id);
                if (user == null) {
                    resp.sendRedirect(req.getContextPath() + "/user?msg=not_found");
                    return;
                }
                req.setAttribute("user", user);
                
                Map<String , List<Tasks>> group = taskServices.getTaskByStatus(user_id);
                req.setAttribute("todo", group.get("todo"));
                req.setAttribute("inProgress", group.get("inProgress"));
                req.setAttribute("done", group.get("done"));
                
                
                Map<String, Integer> percents = taskServices.getTaskPercentages(user_id);
                req.setAttribute("percentTodo", percents.get("percentTodo"));
                req.setAttribute("percentInProgress", percents.get("percentInProgress"));
                req.setAttribute("percentDone", percents.get("percentDone"));
                
                req.getRequestDispatcher("user-detail.jsp").forward(req, resp);
				return;
			}
			case "/user-delete":{
				String idStr = req.getParameter("id");
				if (idStr == null || idStr.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/user?msg=missing_id");
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/user?msg=bad_id");
                    return;
                }
                boolean ok = userServices.deleteByID(id);
                resp.sendRedirect(req.getContextPath() + "/user?msg=" + (ok ? "deleted" : "delete_failed"));
                return;
			}
			
			default:
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.println("Loi GET user: "+ e.getMessage());
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/user?msg=error");
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();
		try {
			switch(path) {
			case "/user-add":{
				String email = req.getParameter("email");
				String fullname = req.getParameter("fullname");
				String password = req.getParameter("password");
				String country = req.getParameter("country");
				String roleIdStr = req.getParameter("roleId"); // luôn trả về String
				int role_id = 0;
				// happy case
				try {
                    role_id = Integer.parseInt(roleIdStr);
                } catch (Exception ex) {
                    req.setAttribute("error", "Vui lòng chọn Role hợp lệ!");
                    req.setAttribute("roles", rolesService.getAllRole()); // [SỬA] nạp lại roles khi trả form
                    req.getRequestDispatcher("user-add.jsp").forward(req, resp); // [SỬA] forward để giữ message + dữ liệu
                    return;
                }
				
				try {
					boolean ok = userServices.insertUser(email, password, fullname, country, role_id);
					resp.sendRedirect(req.getContextPath() + (ok ? "/user-add?msg=created" : "/user-add?msg=failed"));
					return;
				} catch (Exception e) {
					e.printStackTrace();
					resp.sendRedirect(req.getContextPath() + "/user-add?msg=error");
                    return;
				}
			}
			case "/user-edit":{
				String idStr = req.getParameter("id");
                String fullname = req.getParameter("fullname");
                String email = req.getParameter("email");
                String country = req.getParameter("country");
                if (idStr == null || idStr.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/user?msg=missing_id");
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/user?msg=bad_id");
                    return;
                }

                boolean ok = email != null && !fullname.trim().isEmpty() && country != null
                           && userServices.updateUserById(id, email, fullname, country);

                resp.sendRedirect(req.getContextPath() + (ok ? "/user?msg=updated"
                                                             : "/user-edit?id=" + id + "&msg=failed"));
                return;
			}
			
			
			default:
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.println("Loi POST user: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
