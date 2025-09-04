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
@WebFilter(filterName ="authenFilter",urlPatterns = {"/user"})
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
        
        Cookie[] cookies = req.getCookies();
        //goi boolean vi khi xu ly logic code trong vong lap for de bi goi 2 cookie
        boolean isLogined = false;

        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals("role")) {
                isLogined =true;
            }
        }
        if(isLogined) {
        	chain.doFilter(request, response);
        }else {
        	//khong co req.getContextPath() thi mat cai crm_app10 (lay dong)
        	resp.sendRedirect(req.getContextPath() + "/login");
        }

        
		System.out.println("ktr filter");

    }
	
}
