package crm_app10.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.MySQLConfig;
import entity.Users;
@WebServlet(name="loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Cookie[] listCookie = req.getCookies();
		String email = "";
		String password = "";
		if (listCookie != null) {                  // <- THÊM DÒNG NÀY
		        for (Cookie cookie : listCookie) {
		            String name = cookie.getName();
		            String value = cookie.getValue();
		            if ("email".equals(name))  email = value;
		            if ("password".equals(name)) password = value; // (không khuyến khích lưu pass)
		        }
		}
		req.setAttribute("email", email);
		//req.setAttribute("password", password);
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String remember =req.getParameter("remember");
		
		//Chuan bi cau truy van
		String query ="SELECT *\r\n"
				+ "FROM users u\r\n"
				+ "WHERE u.email = ? AND u.password = ?";
		
		//mo ket noi co so du lieu
		Connection connection = MySQLConfig.getConnection();
		try {
			//truyen cau truy van
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			//Set tham so cho dau ? ben trong cau query (vitridau chamhoi,bien)
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			/*
			 * excuteQuery: SELECT
			 * excuteUpdate: khong phai la cau SELECT
			 */
			ResultSet resultSet = preparedStatement.executeQuery();
			//Tao mot danh sach rong de bien du lieu tu cau truy van trong result set thanh mang/danhsach
			List<Users> listUser = new ArrayList<>();
			// duyet qua tung dong du lieu
			while (resultSet.next()){
				Users users = new Users();
				//id voi fullname nay` la ten cot trong database
				users.setId(resultSet.getInt("id"));
				users.setName(resultSet.getString("fullname"));
				users.setRoleID(resultSet.getInt("role_id"));
				listUser.add(users);
			}
			
			if(listUser.isEmpty()) {
				System.out.println("Dang nhap that bai");
				req.setAttribute("error", "Sai email hoặc mật khẩu");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
				return;
			}else {
				System.out.println("Dang nhap thanh cong");
				// tao cookie can du 3 dong ben duoi cho moi cookie
				Cookie cRole = new Cookie("role",listUser.get(0).getRoleID()+"");
				cRole.setMaxAge(8*60*60);
				resp.addCookie(cRole);
				
				int user_id = listUser.get(0).getId();
				Cookie cUserId = new Cookie("uid", String.valueOf(user_id));
				// Quan trọng: đặt Path để cookie dùng được ở các URL khác (profile, task, ...)
				cUserId.setPath((req.getContextPath() == null || req.getContextPath().isEmpty()) ? "/" : req.getContextPath());
				
				int maxAge = (remember != null) ? (7*24*60*60) : (8*60*60);
				cUserId.setMaxAge(maxAge);

				resp.addCookie(cUserId);
				if(remember!=null) {
					//Tao cookie
					//Tao cookie co ten la email va gia tri luu tru la email nguoi dung nhap
					Cookie cEmail = new Cookie("email",email);
					cEmail.setMaxAge(60*60);// quy doi ra giay(s)
					Cookie cPassword = new Cookie("password", password);
					cPassword.setMaxAge(60*60);
					
					//bat client tao ra cookie 
					resp.addCookie(cPassword);
					resp.addCookie(cEmail);
				}
				resp.sendRedirect(req.getContextPath() + "/dashboard.jsp");
			}

		} catch (Exception e) {
			System.out.println("Lỗi đăng nhập: " + e.getMessage());
		}
		
		System.out.println("Gia tri : "+ email + " - "+ password);
		
	}
}