package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebFilter(filterName ="authenFilter",urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {
	/*
	 * check xem nguoi dung co dang nhap chua. Neu chua dang nhap da ve trang login, neu roi thi cho di tiep
	 * */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//cho di tiep
		//chain.doFilter(request, response);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        String path = req.getServletPath();
        String ctx = req.getContextPath();
        
        if (path.startsWith("/login") || path.startsWith("/assets")|| path.startsWith("/plugins")) {
        	chain.doFilter(req, resp);
        	return;
        }
        
        String role = null;
        Cookie[] cookies = req.getCookies();
        if(cookies!= null) {
        	for(Cookie c : cookies) {
        		if("role".equals(c.getName())) {
        			role = c.getValue();
            		break;
        		}
        		
        	}
        }
        
        // ==== PHÂN QUYỀN THEO ROLE & USE CASE ====
        if (path.startsWith("/user") || path.startsWith("/roles")) {
            if (!"1".equals(role)) {
                resp.sendRedirect(ctx + "/403.jsp");
                return;
            }
        }
        
        if (path.startsWith("/job") || path.startsWith("/task")) {
            if (!("1".equals(role) || "2".equals(role))) {
                resp.sendRedirect(ctx + "/403.jsp");
                return;
            }
        }
        
     // Member chỉ có quyền task cá nhân, profile cá nhân
        if (path.startsWith("/profile")) {
            // tất cả role đều dùng được
        }

        // Nếu hợp lệ
        chain.doFilter(request, response);
        
    }
	
}
