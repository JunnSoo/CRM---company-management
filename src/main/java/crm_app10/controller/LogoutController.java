package crm_app10.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name ="logoutController", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		
		Cookie[] listCookie = req.getCookies();
		if(listCookie != null) {
			for(Cookie c : listCookie) {
				if ("email".equals(c.getName()) 
			            || "password".equals(c.getName()) 
			            || "role".equals(c.getName()) 
			            || "uid".equals(c.getName()))  {
					c.setMaxAge(0);
					resp.addCookie(c);
				}
			}
		}
		resp.sendRedirect(req.getContextPath() + "/login");
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
