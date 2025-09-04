package crm_app10.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app10.services.RolesServices;
import entity.Roles;

@WebServlet(name = "rolesController", urlPatterns = { "/roles", "/roles-add" })
public class RolesController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final RolesServices rolesService = new RolesServices();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();
        if ("/roles".equals(path)) {
            // List roles
            List<Roles> listRoles = rolesService.getAllRole();
            req.setAttribute("listRole", listRoles);
            req.getRequestDispatcher("role_table.jsp").forward(req, resp);
        } else if ("/roles-add".equals(path)) {
            // Show create form
            req.getRequestDispatcher("role-add.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();
        if ("/roles-add".equals(path)) {
            req.setCharacterEncoding("UTF-8");
            String roleName = req.getParameter("roleName");
            String desc = req.getParameter("desc");

            try {
                boolean ok = roleName != null && !roleName.trim().isEmpty()
                           && rolesService.insertRole(roleName.trim(), desc);

                // PRG: tránh submit lại khi F5
                if (ok) {
                    resp.sendRedirect(req.getContextPath() + "/roles?msg=created");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/roles-add?msg=failed");
                }
            } catch (Exception e) {
            	System.out.println("Loi them quyen: " + e.getMessage());
                resp.sendRedirect(req.getContextPath() + "/roles-add?msg=error");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }
}
