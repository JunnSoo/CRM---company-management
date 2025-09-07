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

@WebServlet(
    name = "rolesController",
    urlPatterns = { "/roles", "/roles-add", "/roles-edit", "/roles-delete" } // <-- thêm đủ path
)
public class RolesController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final RolesServices rolesService = new RolesServices();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();

        try {
            switch (path) {
                case "/roles": {
                    List<Roles> listRoles = rolesService.getAllRole();
                    req.setAttribute("listRole", listRoles);
                    req.getRequestDispatcher("role_table.jsp").forward(req, resp);
                    return;
                }
                case "/roles-add": {
                    req.getRequestDispatcher("role-add.jsp").forward(req, resp);
                    return;
                }
                case "/roles-edit": {
                    String idStr = req.getParameter("id");
                    if (idStr == null || idStr.isEmpty()) {
                        resp.sendRedirect(req.getContextPath() + "/roles?msg=missing_id");
                        return;
                    }
                    int id;
                    try {
                        id = Integer.parseInt(idStr);
                    } catch (NumberFormatException e) {
                        resp.sendRedirect(req.getContextPath() + "/roles?msg=bad_id");
                        return;
                    }
                    Roles role = rolesService.getRoleById(id);
                    if (role == null) {
                        resp.sendRedirect(req.getContextPath() + "/roles?msg=not_found");
                        return;
                    }
                    req.setAttribute("role", role);
                    req.getRequestDispatcher("role-edit.jsp").forward(req, resp);
                    return;
                }
                case "/roles-delete": { // GET delete theo yêu cầu
                    String idStr = req.getParameter("id");
                    if (idStr == null || idStr.isEmpty()) {
                        resp.sendRedirect(req.getContextPath() + "/roles?msg=missing_id");
                        return;
                    }
                    int id;
                    try {
                        id = Integer.parseInt(idStr);
                    } catch (NumberFormatException e) {
                        resp.sendRedirect(req.getContextPath() + "/roles?msg=bad_id");
                        return;
                    }
                    boolean ok = rolesService.deleteByID(id);
                    resp.sendRedirect(req.getContextPath() + "/roles?msg=" + (ok ? "deleted" : "delete_failed"));
                    return;
                }
                default: {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception ex) {
            System.out.println("Loi GET roles: " + ex.getMessage());
            resp.sendRedirect(req.getContextPath() + "/roles?msg=error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8"); // <-- đặt sớm trước khi đọc param
        String path = req.getServletPath();

        try {
            switch (path) {
                case "/roles-add": {
                    String roleName = req.getParameter("roleName");
                    String desc = req.getParameter("desc");

                    try {
                        boolean ok = roleName != null && !roleName.trim().isEmpty()
                                   && rolesService.insertRole(roleName.trim(), desc);

                        resp.sendRedirect(req.getContextPath() + (ok
                                ? "/roles?msg=created"
                                : "/roles-add?msg=failed"));
                        return; // tránh fall-through
                    } catch (Exception e) {
                        // log chi tiết nếu cần: e.printStackTrace();
                        resp.sendRedirect(req.getContextPath() + "/roles-add?msg=error");
                        return;
                    }
                }
                case "/roles-edit": {
                    String idStr = req.getParameter("id");
                    String roleName = req.getParameter("roleName");
                    String desc = req.getParameter("desc");

                    if (idStr == null || idStr.isEmpty()) {
                        resp.sendRedirect(req.getContextPath() + "/roles?msg=missing_id");
                        return;
                    }
                    int id;
                    try {
                        id = Integer.parseInt(idStr);
                    } catch (NumberFormatException e) {
                        resp.sendRedirect(req.getContextPath() + "/roles?msg=bad_id");
                        return;
                    }

                    boolean ok = roleName != null && !roleName.trim().isEmpty()
                               && rolesService.updateRole(id, roleName.trim(), desc);

                    resp.sendRedirect(req.getContextPath() + (ok ? "/roles?msg=updated"
                                                                 : "/roles-edit?id=" + id + "&msg=failed"));
                    return;
                }
                default: {
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                }
            }
        } catch (Exception e) {
            System.out.println("Loi POST roles: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/roles?msg=error");
        }
    }
}
